package com.example.searchengine.components.crawler;



import com.example.searchengine.components.services.JsonFileService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Service
public class CrawlerService {
    JsonFileService jsonFileService=new JsonFileService();

    public boolean checkIfPageInJsonFile(String url, String jsonFile){

        JSONArray jsonDataArray=jsonFileService.readJsonFile(jsonFile);

        for(Object urlData:jsonDataArray){
            JSONObject urlDataObject=(JSONObject) urlData;
            if(urlDataObject.get("url").equals(url)){
                return true;
            }
        }

        return false;
    }

    public void incrementBackLinkCount(String url){
        JSONObject jsonObject=jsonFileService.readJsonFileObject("backLinkCount.Json");
        Integer idfCount=getBackLinkCount(url);
        jsonObject.put(url, idfCount+1);

        JsonFileService.writeJsonToFileObject(jsonObject, "backLinkCount.json");

    }
    private Integer getBackLinkCount(String term){
        JSONObject jsonObject=jsonFileService.readJsonFileObject("backLinkCount.Json");
        try {
            return jsonObject.getInt(term);
        } catch (JSONException e) {
            return 0;
        }
    }

    public void addPageToJson(String url){
        UrlDataJsonObject urlDataJsonObject= new UrlDataJsonObject("data.json", url);
        urlDataJsonObject.addUrlDataToJsonFile();
    }
}
