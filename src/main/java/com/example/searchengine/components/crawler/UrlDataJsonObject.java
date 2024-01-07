package com.example.searchengine.components.crawler;

import com.example.searchengine.components.services.JsonFileService;
import org.json.JSONObject;

public class UrlDataJsonObject {
    public String jsonFile;
    public String pageUrl;

    public UrlDataJsonObject(String jsonFile,
                             String pageUrl){

        this.jsonFile=jsonFile;
        this.pageUrl=pageUrl;
    }


    public void addUrlDataToJsonFile(){
        Website website=new Website(pageUrl);
        Rating rating=new Rating(website);
        JsonFileService jsonFileService=new JsonFileService();


        JSONObject websiteObjcet=website.getWebsiteData(rating.getPageRating());

        jsonFileService.appendObjectToFile(websiteObjcet,"data.json");

    }


}
