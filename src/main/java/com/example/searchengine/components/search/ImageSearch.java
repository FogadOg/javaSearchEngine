package com.example.searchengine.components.search;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageSearch extends Search{

    private final JSONArray websites;
    private final String searchTerm;

    public ImageSearch(JSONArray websiteData, String searchTerm){

        this.websites=websiteData;
        this.searchTerm=searchTerm;
    }

    public JSONArray search(){
        JSONArray relevantImages=new JSONArray();

        for(int index=0;index<websites.length(); index++){
            JSONObject website= (JSONObject) websites.get(index);
            JSONArray images = (JSONArray) website.get("images");

            for(Object image: images){
                JSONObject imageObject= (JSONObject) image;
                String imageAlt = imageObject.get("alt").toString();
                if(isImageRelevant(imageAlt, .1)){
                    relevantImages.put(image);
                }
            }
        }


        return relevantImages;
    }

    private Boolean isImageRelevant(String altText, Double threshHold){
        return calculateTextsSimilarity(searchTerm, altText) >= threshHold;
    }


}
