package com.example.searchengine.components.crawler;



import com.example.searchengine.components.JsonFileService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.stereotype.Service;


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

    public static void main(String[] args){

    }
}
