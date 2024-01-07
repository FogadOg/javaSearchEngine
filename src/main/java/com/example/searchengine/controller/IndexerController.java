package com.example.searchengine.controller;

import com.example.searchengine.components.services.JsonFileService;
import com.example.searchengine.components.Indexing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class IndexerController {
    Indexing indexer = new Indexing("indexMap.json");
    JsonFileService jsonFileService=new JsonFileService();

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/index")
    public void runIndexer() {
            JSONArray jsonDataArray = jsonFileService.readJsonFile("data.json");

            for (int index = 0; index<jsonDataArray.length(); index++) {
                JSONObject urlDataObject = (JSONObject) jsonDataArray.get(index);
                Object contentObject = urlDataObject.get("content");

                if (contentObject instanceof JSONArray contentArray) {
                    StringBuilder joinedStringBuilder = new StringBuilder();

                    for (Object content : contentArray) {
                        joinedStringBuilder.append(content.toString());
                    }
                    String joinedString = joinedStringBuilder.toString();

                    indexer.mapDocument(
                            joinedString,
                            Integer.toString(index),
                            .2
                    );
                }
            }
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/index/data")
    public JSONArray viewIndexing(){
        return jsonFileService.readJsonFile("indexMap.json");

    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/index/{term}")
    public JSONArray getMapping(@PathVariable String term){
        return indexer.getTermMapping(term);
    }
}
