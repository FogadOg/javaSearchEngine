package com.example.searchengine.components.search;

import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.stemmer.Stemmer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;

public class WebsiteSearch extends Search{

    public String filePath="data.json";

    public JSONArray getAllWebsites(String searchTerm){

        return searchForRelevantWebsites(searchTerm);

    }

    private JSONArray searchForRelevantWebsites(String searchTrem){
        JsonFileService jsonFileService=new JsonFileService();

        JSONArray jsonArray=jsonFileService.readJsonFile(filePath);

        Object object=jsonFileService.findObject(jsonArray, "url");

        System.out.println("object: "+object);
        //String content=jsonFileService.objectToString(object.get);

        return jsonArray;
    }

}
