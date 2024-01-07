package com.example.searchengine.components.search;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageSearch extends Search{

    private final JSONArray websites;

    public ImageSearch(JSONArray websiteData){
        this.websites=websiteData;
    }

    public JSONArray search(String searchTerm){
        JSONArray relevantImages=new JSONArray();

        for(int index=0;index<websites.length(); index++){
            JSONObject website= (JSONObject) websites.get(index);
            JSONArray images = (JSONArray) website.get("images");

            for(Object image: images){
                relevantImages.put(image);
            }

        }


        return relevantImages;
    }


}
