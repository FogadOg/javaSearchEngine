package com.example.searchengine.components.crawler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class UrlDataJsonObject {
    public String jsonFile;
    public String url;
    public LocalDateTime lastTimeCrawled;
    public String content;
    public Integer rating;

    public UrlDataJsonObject(String jsonFile, String url, LocalDateTime lastTimeCrawled, String content, Integer rating){

        this.jsonFile=jsonFile;
        this.url=url;
        this.lastTimeCrawled=lastTimeCrawled;
        this.content=content;
        this.rating=rating;
    }

    public void addUrlDataToJsonFile(){
        try {
            FileReader fileReader = new FileReader(jsonFile);
            JSONTokener tokener = new JSONTokener(fileReader);
            JSONArray jsonArray = new JSONArray(tokener);

            JSONObject newObject = new JSONObject();
            newObject.put("lastTimeCrawled", lastTimeCrawled);
            newObject.put("rating", rating);
            newObject.put("url", url);
            newObject.put("content", content);

            jsonArray.put(newObject);

            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonArray.toString(4));
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
