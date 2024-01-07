package com.example.searchengine.components.crawler;

import com.example.searchengine.components.crawler.Website;
import org.json.JSONArray;
import org.json.JSONObject;

public class Rating {
    private final Website website;
    public Rating(Website website){
        this.website=website;

    }
    public Integer getPageRating(){

        int responseTimeSqrt=(int) Math.sqrt(website.pageResponseTime);

        return presentOfGoodImages()*responseTimeSqrt;
    }

    private Integer presentOfGoodImages(){
        int goodAltAttributes=0;
        JSONArray images=website.pageImages;

        for(int i = 0; i< images.length(); i++){
            JSONObject imageData= images.getJSONObject(i);

            if(imageData.get("alt").toString().isBlank()){
                goodAltAttributes+=1;
            }
        }

        return (goodAltAttributes/images.length())*100;
    }

}
