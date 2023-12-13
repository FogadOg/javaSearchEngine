package com.example.searchengine.components.crawler;

import com.example.searchengine.components.stemmer.Stemmer;
import com.gargoylesoftware.htmlunit.html.*;
import com.google.gson.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.gargoylesoftware.htmlunit.WebClient;

import javax.swing.text.Document;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlDataJsonObject {
    public String jsonFile;
    public Integer pageResponseTime;
    public String pageUrl;
    public String rootUrl;
    public HtmlPage websidePage;
    public Stemmer stemmer;

    public UrlDataJsonObject(String jsonFile,
                             String pageUrl){

        this.jsonFile=jsonFile;
        this.pageUrl=pageUrl;
        this.rootUrl=getRootUrl();
        this.stemmer = new Stemmer();


        this.websidePage = getHtmlContent();
    }

    private String getRootUrl(){
        String rootUrl = "";
        try {
            URL url = new URL(pageUrl);
            rootUrl = url.getProtocol() + "://" + url.getHost();
            return rootUrl;
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + pageUrl);
        }

        return rootUrl;
    }

    public void addUrlDataToJsonFile(){

        try {
            FileReader fileReader = new FileReader(jsonFile);
           JSONTokener tokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(tokener);
            JSONObject newObject = new JSONObject();

            UUID uuid=UUID.randomUUID();


            JSONArray numberOfwebsiteImages=getPageImages();

            newObject.put("pageId", uuid);
            newObject.put("lastTimeCrawled",  LocalDateTime.now());
            newObject.put("pageTitle", getPagesTitle());
            newObject.put("pageName", getWebsiteName());
            newObject.put("favicon", getPageFaviconPath());
            Rating rating = new Rating(pageResponseTime,numberOfwebsiteImages);
            newObject.put("rating", rating.getPageRating());
            newObject.put("url", pageUrl);
            newObject.put("content", getPageContent());
            newObject.put("images", numberOfwebsiteImages);


            jsonArray.put(newObject);

            jsonArray=sortJsonFile(jsonArray);

            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toString(4));
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private JSONArray sortJsonFile(JSONArray jsonArray){

        List<JSONObject> jsonList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonList.add(jsonArray.getJSONObject(i));
        }

        jsonList.sort((o1, o2) -> Integer.compare(o2.getInt("rating"), o1.getInt("rating")));

        jsonArray = new JSONArray(jsonList);
        return jsonArray;
    }

    private String getWebsiteName(){
        Pattern websiteNamePattern = Pattern.compile("\\b(\\w+\\.[a-zA-Z]+)\\/\\b");

        Matcher matcher = websiteNamePattern.matcher(pageUrl);

        if(matcher.find()){
            return matcher.group(1);
        }
        return getPagesTitle();
    }
    private String getPagesTitle(){
        HtmlTitle title = ((HtmlTitle) websidePage.getFirstByXPath("//title"));
        if(title!=null){
            return stemmer.stemString(title.asNormalizedText());
        }
        return "";

    }
    private String getPageFaviconPath(){
        HtmlLink favicon = websidePage.getFirstByXPath("//link[@rel='icon' or @rel='shortcut icon']");

        if(favicon!=null){
            String faviconAttribute = favicon.getHrefAttribute();
            if(faviconAttribute.contains("http")){
                return faviconAttribute;
            }

            return rootUrl+favicon.getHrefAttribute();
        }
        return "";
    }
    private JSONArray getPageContent(){
        JSONArray pageContent=new JSONArray();

        if (websidePage != null) {
            List<?> paragraphs = websidePage.getByXPath("//p");

            for (Object paragraph : paragraphs) {
                if (paragraph instanceof com.gargoylesoftware.htmlunit.html.HtmlElement) {
                    String paragraphText = ((com.gargoylesoftware.htmlunit.html.HtmlElement) paragraph).getTextContent().trim();
                    if(!paragraphText.isEmpty()){

                        String stemmedParagraph=stemmer.stemString(paragraphText);
                        pageContent.put(stemmedParagraph);
                    }
                }
            }
        }

        return pageContent;


    }
    private JSONArray getPageImages(){
        JSONArray imageSources = new JSONArray();


        if (websidePage != null) {
            List<?> images = websidePage.getByXPath("//img");

            for (Object image : images) {
                JSONObject imageData= new JSONObject();
                if (image instanceof com.gargoylesoftware.htmlunit.html.HtmlImage) {
                    String srcTag = ((com.gargoylesoftware.htmlunit.html.HtmlImage) image).getSrcAttribute();
                    String altTag = ((com.gargoylesoftware.htmlunit.html.HtmlImage) image).getAltAttribute();
                    if (srcTag != null && !srcTag.isEmpty()) {
                        if(!srcTag.contains(rootUrl)){
                            imageData.put("src",rootUrl+srcTag);
                            imageData.put("alt",stemmer.stemString(altTag));
                            imageSources.put(imageData);
                        }else{
                            imageData.put("src",srcTag);
                            imageData.put("alt",stemmer.stemString(altTag));
                            imageSources.put(imageData);
                        }

                    }
                }
            }
        }


        return imageSources;

    }
    private HtmlPage getHtmlContent(){

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage page=null;
        try{
            Instant beforeRequest=Instant.now();
            page = client.getPage(pageUrl);
            Instant afterRequest=Instant.now();

            pageResponseTime= Duration.between(beforeRequest, afterRequest).toMillisPart();



        }catch (IOException e){
            e.printStackTrace();
            return page;

        }



        return page;

    }

}
