package com.example.searchengine.components;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class JsonFileService {

    public JSONArray readJsonFile(String filePath) {
        JSONArray emptyJSONArray = new JSONArray();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONArray(content);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return emptyJSONArray;
    }

    public JSONObject findObject(JSONArray jsonDataArray, String url) {
        for (int i = 0; i < jsonDataArray.length(); i++) {
            JSONObject website = jsonDataArray.getJSONObject(i);
            String websiteUrl = website.getString("url");

            if (websiteUrl.equals(url)) {
                return website;
            }
        }
        return null;
    }

    public String objectToString(JSONObject object) {
        return object.toString();
    }
}
