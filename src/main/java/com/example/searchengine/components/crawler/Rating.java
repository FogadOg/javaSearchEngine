package com.example.searchengine.components.crawler;

import com.example.searchengine.components.crawler.Website;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class Rating {
    private final Website website;
    private final Integer initialRating;
    public Rating(Website website){
        this.website=website;

        this.initialRating = 100;
    }
    public Integer getPageRating(){
        int responseTimeSqrt=(int) Math.sqrt(website.pageResponseTime);

        Integer goodImages=imagesQuality();
        double rating=Math.pow(goodImages, 2) + initialRating;

        return Math.toIntExact(Math.round(rating * Math.sqrt(responseTimeSqrt)));
    }

    private Integer imagesQuality(){
        int goodAltAttributes=0;

        JSONArray images=website.pageImages;

        for(int i = 0; i< images.length(); i++){
            JSONObject imageData= images.getJSONObject(i);

            if(!imageData.get("alt").toString().isBlank()){
                goodAltAttributes+=1;
            }
        }

        return goodAltAttributes;
    }

}
