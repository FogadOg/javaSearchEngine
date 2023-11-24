package com.example.searchengine.components.crawler;

import com.gargoylesoftware.htmlunit.html.*;
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
import java.time.LocalDateTime;
import java.util.List;

public class UrlDataJsonObject {
    public String jsonFile;
    public String pageUrl;
    public LocalDateTime lastTimeCrawled;
    public String content;
    public Integer rating;
    public HtmlPage websidePage;

    public UrlDataJsonObject(String jsonFile,
                             String pageUrl,
                             LocalDateTime lastTimeCrawled,
                             String content,
                             Integer rating){

        this.jsonFile=jsonFile;
        this.pageUrl=pageUrl;

        this.lastTimeCrawled=lastTimeCrawled;
        this.content=content;
        this.rating=rating;


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




            newObject.put("lastTimeCrawled", lastTimeCrawled);
            newObject.put("pageTitle", getPagesTitle());
            newObject.put("favicon", getRootUrl()+getPageFaviconPath());
            newObject.put("rating", rating);
            newObject.put("url", pageUrl);
            newObject.put("content", content);

            jsonArray.put(newObject);

            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toString(4));
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    private String getPagesTitle(){
        HtmlTitle title = ((HtmlTitle) websidePage.getFirstByXPath("//title"));
        if(title!=null){
            return title.asNormalizedText();
        }
        return "";

    }
    private String getPageFaviconPath(){
        HtmlLink favicon = websidePage.getFirstByXPath("//link[@rel='icon' or @rel='shortcut icon']");

        if(favicon!=null){
            return favicon.getHrefAttribute();
        }
        return "";
    }


    private HtmlPage getHtmlContent(){

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage page=null;
        try{
            page = client.getPage(pageUrl);
        }catch (IOException e){
            e.printStackTrace();
            return page;

        }



        return page;

    }

}
