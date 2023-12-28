package com.example.searchengine.components.search;

import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.indexing.Indexing;
import com.example.searchengine.components.indexing.PreprocessText;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List;

public class WebsiteSearch extends Search{

    public String filePath="data.json";

    public JSONArray getAllWebsites(String searchQuery){

        JsonFileService jsonFileService=new JsonFileService();
        Indexing indexer= new Indexing("indexMap.json");
        JSONArray relevantWebsites=new JSONArray();

        PreprocessText processer = new PreprocessText();

        List<String> querys=processer.processForIndexing(searchQuery);

        for(String query: querys){
            JSONArray indexMappingArray=indexer.getTermMaping(query);

            for(Object website: indexMappingArray){
                JSONObject websiteObject=(JSONObject) website;
                String url=websiteObject.get("id").toString();
                JSONArray jsonArray=jsonFileService.readJsonFile(filePath);
                JSONObject object=(JSONObject) jsonFileService.findObject(jsonArray, url);

                if(object!=null){
                    String content=jsonFileService.objectToString(object.get("content"));
                    if(getSimilarityOfSearchAndWebsite(searchQuery,content)>=0.9){
                        relevantWebsites.add(object);
                    }


                }
            }
        }

        return relevantWebsites;

    }
}
