package com.example.searchengine.components.search;

import com.example.searchengine.components.services.JsonFileService;
import com.example.searchengine.components.services.WebsiteService;
import com.example.searchengine.components.Indexing;
import com.example.searchengine.components.textProcessers.PreprocessText;
import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.Math;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class WebsiteSearch extends Search{

    public String filePath="data.json";

    JsonFileService jsonFileService=new JsonFileService();
    Indexing indexer= new Indexing("indexMap.json");
    JSONArray relevantWebsites=new JSONArray();
    PreprocessText processor = new PreprocessText();

    WebsiteService websiteService=new WebsiteService();

    public JSONArray getAllWebsites(String searchQuery){
        Instant start=Instant.now();
        List<String> queryList= processor.processForIndexing(searchQuery);

        for(String query: queryList){
            JSONArray indexMappingArray=indexer.getTermMapping(query);

            for(Object website: indexMappingArray){
                if (website instanceof JSONObject websiteObject) {
                    String id=websiteObject.get("id").toString();
                    JSONArray jsonArray=jsonFileService.readJsonFile(filePath);
                    JSONObject object=jsonFileService.getObject(jsonArray, Integer.valueOf(id));

                    System.out.println("1: "+object.get("rating"));
                    calculateRatingRelativeToBackLink(object,id);
                    System.out.println("2: "+object.get("rating"));

                    if(object!=null) {
                        String newContent=getMostRelevantString((JSONArray) object.get("content"),searchQuery);
                        relevantWebsites.put(websiteService.replaceField(object,"content",newContent));

                    }
                }
            }
        }
        Instant end=Instant.now();
        System.out.println("response time: "+ Duration.between(start,end).toMillis()+"ms");

        return relevantWebsites;

    }

    private void calculateRatingRelativeToBackLink(JSONObject object,String url){
        Integer backLinkAmount=jsonFileService.getKeyValue(url,"backLinkCount.json");
        double result = Math.sqrt(backLinkAmount);

        Integer rating =(Integer) object.get("rating");
        object.put("rating",rating*result);
    }

    private String getMostRelevantString(JSONArray jsonArray, String searchQuery){
        //String content="";
        //int currentHighScore=0;
        //for(Object contentString: jsonArray){

        //    if(calculateTextsSimilarity(searchQuery, contentString.toString())>currentHighScore){
        //        content=contentString.toString();

        //    }
        // }


        return jsonArray.get(0).toString();
    }
}
