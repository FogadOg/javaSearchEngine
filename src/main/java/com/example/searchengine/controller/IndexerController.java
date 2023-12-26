package com.example.searchengine.controller;

import com.example.searchengine.components.indexing.Indexing;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/index")
    public void runIndexer() {
        Indexing indexer = new Indexing();
        JSONParser jsonParser = new JSONParser();

        try {
            JSONArray jsonDataArray = (JSONArray) jsonParser.parse(new FileReader("data.json"));

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
                            0.0005
                    );
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/index/view")
    public void viewIndexing(){


    }
}
