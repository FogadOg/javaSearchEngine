package com.example.searchengine.components;

import com.example.searchengine.components.tfIdf.TfIdfService;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTitle;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Website {
    private final HtmlPage websitePage;
    public String pageUrl;
    public String rootUrl;
    public Integer pageResponseTime;

    public JSONArray content;
    public JSONArray pageImages;
    public String pageTitle;
    public String pageName;
    public String favicon;

    public Website(String pageUrl){
        this.pageUrl = pageUrl;

        this.rootUrl=getRootUrl();
        this.websitePage = getHtmlContent();


        this.content=getPageContent();
        this.pageImages=getPageImages();
        this.pageTitle=getPagesTitle();
        this.pageName=getWebsiteName();
        this.favicon=getPageFaviconPath();
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
        HtmlTitle title = ((HtmlTitle) websitePage.getFirstByXPath("//title"));
        if(title!=null){
            return title.asNormalizedText();
        }
        return "";

    }
    private String getPageFaviconPath(){
        HtmlLink favicon = websitePage.getFirstByXPath("//link[@rel='icon' or @rel='shortcut icon']");

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

        if (websitePage != null) {
            List<?> paragraphs = websitePage.getByXPath("//p");

            for (Object paragraph : paragraphs) {
                if (paragraph instanceof com.gargoylesoftware.htmlunit.html.HtmlElement) {
                    String paragraphText = ((com.gargoylesoftware.htmlunit.html.HtmlElement) paragraph).getTextContent().trim();
                    if(!paragraphText.isEmpty()){

                        pageContent.put(paragraphText);
                    }
                }
            }
        }

        return pageContent;


    }
    private JSONArray getPageImages() {
        JSONArray imageSources = new JSONArray();

        if (websitePage != null) {
            List<?> images = websitePage.getByXPath("//img");

            for (Object image : images) {
                JSONObject imageData = new JSONObject();
                if (image instanceof com.gargoylesoftware.htmlunit.html.HtmlImage) {
                    String srcTag = ((com.gargoylesoftware.htmlunit.html.HtmlImage) image).getSrcAttribute();
                    String altTag = ((com.gargoylesoftware.htmlunit.html.HtmlImage) image).getAltAttribute();
                    if (srcTag != null && !srcTag.isEmpty()) {
                        try {
                            if (!srcTag.contains(rootUrl)) {
                                if (isValidImageUrl(rootUrl + srcTag)) {
                                    imageData.put("src", rootUrl + srcTag);
                                    imageData.put("alt", altTag);
                                    imageSources.put(imageData);
                                }
                            } else {
                                if (isValidImageUrl(srcTag)) {
                                    imageData.put("src", srcTag);
                                    imageData.put("alt", altTag);
                                    imageSources.put(imageData);
                                }
                            }
                        } catch (IOException | URISyntaxException e) {
                            // Handle the exception or log it
                            // Maybe skip adding the invalid URL to imageSources
                            System.err.println("Invalid URL: " + e.getMessage());
                        }
                    }
                }
            }
        }
        return imageSources;
    }
    private boolean isValidImageUrl(String imageUrl) throws IOException, URISyntaxException {
        try {
            URL url = new URL(imageUrl);
            url.toURI();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    private HtmlPage getHtmlContent(){


        HtmlPage page=null;
        try{

            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

            Instant beforeRequest=Instant.now();
            page = client.getPage(pageUrl);
            Instant afterRequest=Instant.now();

            pageResponseTime= getPageResponseTime(beforeRequest, afterRequest);



        }catch (IOException e){
            e.printStackTrace();
            return page;

        }



        return page;

    }

    public Integer getPageResponseTime(Instant beforeRequest, Instant afterRequest){
        return Duration.between(beforeRequest, afterRequest).toMillisPart();
    }

    public JSONObject getWebsiteData(Integer rating){
        JSONObject newObject = new JSONObject();

        TfIdfService tfIdfService=new TfIdfService();

        UUID uuid=UUID.randomUUID();


        tfIdfService.incrementIdfCount(content.toString());

        newObject.put("pageId", uuid);
        newObject.put("lastTimeCrawled",  LocalDateTime.now());
        newObject.put("pageTitle", pageTitle);
        newObject.put("pageName", pageName);
        newObject.put("favicon", favicon);
        newObject.put("rating", rating);
        newObject.put("url", pageUrl);
        newObject.put("content", content);
        newObject.put("images", pageImages);

        return newObject;
    }

}
