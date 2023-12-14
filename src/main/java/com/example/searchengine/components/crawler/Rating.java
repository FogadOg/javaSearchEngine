package com.example.searchengine.components.crawler;

import org.json.JSONArray;
import org.json.JSONObject;

public class Rating {
    public Integer pageResponseTime;
    public JSONArray numberOfwebsiteImages;
    public Rating(Integer pageResponseTime, JSONArray numberOfwebsiteImages){
        this.pageResponseTime=pageResponseTime;
        this.numberOfwebsiteImages=numberOfwebsiteImages;

    }
    public Integer getPageRating(){
        Integer initialRating=1000-pageResponseTime;
        Integer numberOfBadAltAttributes=checkImageQuality();

        Integer ratingWithNumberOfImages=initialRating-numberOfBadAltAttributes;

        return ratingWithNumberOfImages+(numberOfwebsiteImages.length()*2);
    }

    private Integer checkImageQuality(){
        Integer badAltAttributes=0;
        for(int i =0; i<numberOfwebsiteImages.length();i++){
            JSONObject imageData=numberOfwebsiteImages.getJSONObject(i);

            if(imageData.get("alt").toString().isBlank()){
                badAltAttributes+=1;
            }
        }

        return badAltAttributes;
    }

}
