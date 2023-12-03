package com.example.searchengine.components.search;

import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Search {
    public String filePath="data.json";

    public JSONArray getAllWebsites(){
        JSONArray jsonArray = new JSONArray();
        JSONParser parser = new JSONParser();

        try{
            JSONArray a = (JSONArray) parser.parse(new FileReader(filePath));

            for (Object o : a)
            {
                JSONObject website = (JSONObject) o;

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("url", website.get("url"));
                jsonObject.put("pageTitle", website.get("pageTitle"));
                jsonObject.put("pageName", website.get("pageName"));
                jsonObject.put("favicon", website.get("favicon"));
                jsonObject.put("rating", website.get("rating"));
                jsonObject.put("lastTimeCrawled", website.get("lastTimeCrawled"));
                jsonObject.put("images", website.get("images"));

                jsonArray.add(jsonObject);


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;


    }

}