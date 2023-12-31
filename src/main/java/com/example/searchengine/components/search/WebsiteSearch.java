package com.example.searchengine.components.search;

import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.indexing.Indexing;
import com.example.searchengine.components.indexing.PreprocessText;
import org.json.JSONArray;
import org.json.JSONObject;

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
            //System.out.println("indexMappingArray: "+indexMappingArray);

            for(Object website: indexMappingArray){
                if (website instanceof JSONObject) {
                    JSONObject websiteObject=(JSONObject) website;
                    String url=websiteObject.get("id").toString();
                    JSONArray jsonArray=jsonFileService.readJsonFile(filePath);
                    JSONObject object=jsonFileService.findObject(jsonArray, url);
                    System.out.println("object: "+object);

                    if(object!=null) {
                        String content = jsonFileService.objectToString((JSONObject) object.get("content"));
                        if (getSimilarityOfSearchAndWebsite(searchQuery, content) >= 0.000) {
                            System.out.println("object added: " + object);

                            relevantWebsites.put(object);
                        }
                    }
                }
            }
        }

        return relevantWebsites;

    }
}
