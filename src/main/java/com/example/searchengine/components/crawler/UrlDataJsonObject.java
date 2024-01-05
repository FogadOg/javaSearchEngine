package com.example.searchengine.components.crawler;

import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.Website;
import com.example.searchengine.components.stemmer.Stemmer;
import com.example.searchengine.components.Rating;
import com.example.searchengine.components.tfIdf.TfIdfService;
import com.gargoylesoftware.htmlunit.html.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.gargoylesoftware.htmlunit.WebClient;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URISyntaxException;
import java.net.HttpURLConnection;

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
