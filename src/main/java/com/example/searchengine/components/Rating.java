package com.example.searchengine.components;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class Rating {
    public Website website;
    public Rating(Website website){
        this.website=website;

    }
    public Integer getPageRating(){
        return 0;
    }

    private Integer checkImageQuality(JSONArray images){
        int badAltAttributes=0;
        for(int i = 0; i< images.length(); i++){
            JSONObject imageData= images.getJSONObject(i);

            if(imageData.get("alt").toString().isBlank()){
                badAltAttributes+=1;
            }
        }

        return badAltAttributes;
    }

}
