package com.example.searchengine.components;

import org.json.JSONArray;
import org.json.JSONObject;

public class Rating {
    public Integer pageResponseTime;
    public JSONArray numberWebsiteImages;
    public Rating(Integer pageResponseTime, JSONArray numberWebsiteImages){
        this.pageResponseTime=pageResponseTime;
        this.numberWebsiteImages =numberWebsiteImages;

    }
    public Integer getPageRating(){
        Integer initialRating=1000-pageResponseTime;
        Integer numberOfBadAltAttributes=checkImageQuality();

        int ratingWithNumberOfImages=initialRating-numberOfBadAltAttributes;

        return ratingWithNumberOfImages+(numberWebsiteImages.length()*2);
    }

    private Integer checkImageQuality(){
        int badAltAttributes=0;
        for(int i = 0; i< numberWebsiteImages.length(); i++){
            JSONObject imageData= numberWebsiteImages.getJSONObject(i);

            if(imageData.get("alt").toString().isBlank()){
                badAltAttributes+=1;
            }
        }

        return badAltAttributes;
    }

}
