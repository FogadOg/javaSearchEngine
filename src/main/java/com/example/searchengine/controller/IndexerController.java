package com.example.searchengine.controller;

import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.indexing.Indexing;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController

public class IndexerController {
    Indexing indexer = new Indexing("indexMap.json");
    JsonFileService jsonFileService=new JsonFileService();

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/index")
    public void runIndexer() {

            JSONArray jsonDataArray = jsonFileService.readJsonFile("data.json");

            for (Object urlData : jsonDataArray) {
                JSONObject urlDataObject = (JSONObject) urlData;
                Object contentObject = urlDataObject.get("content");

                if (contentObject instanceof JSONArray contentArray) {
                    StringBuilder joinedStringBuilder = new StringBuilder();

                    for (Object content : contentArray) {
                        joinedStringBuilder.append(content.toString());
                    }
                    String joinedString = joinedStringBuilder.toString();

                    indexer.mapDocument(
                            joinedString,
                            urlDataObject.get("url").toString(),
                            .05
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
    @GetMapping("/index/tester")
    public void indexTester(){
        System.out.println(indexer.getTermMapping("estas"));

    }
}
