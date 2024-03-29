package com.example.searchengine.components.services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;


@Service
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
    public JSONObject readJsonFileObject(String filePath) {
        JSONObject emptyJSONObject = new JSONObject();

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONObject(content);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return emptyJSONObject;
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
    public JSONObject getObject(JSONArray jsonDataArray, Integer index) {
        return jsonDataArray.getJSONObject(index);
    }
    public void writeJsonToFile(JSONObject jsonObject, String fileName) {
        try {
            JSONParser parser = new JSONParser();

            JSONArray jsonArray;
            try (FileReader reader = new FileReader(fileName)) {
                Object obj = parser.parse(reader);
                if (obj instanceof JSONArray) {
                    jsonArray = (JSONArray) obj;
                } else {
                    jsonArray = new JSONArray();
                }
            } catch (FileNotFoundException e) {
                jsonArray = new JSONArray();
            }

            jsonArray.put(jsonObject);

            try (FileWriter file = new FileWriter(fileName)) {
                file.write(jsonArray.toString());
                file.flush();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public static void appendObjectToFile(JSONObject jsonObject, String filePath) {
        try {
            JSONArray jsonArray;

            try {
                String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
                jsonArray = new JSONArray(fileContent);
            } catch (IOException | JSONException e) {
                jsonArray = new JSONArray();
            }

            jsonArray.put(jsonObject);

            try (FileWriter file = new FileWriter(filePath)) {
                file.write(jsonArray.toString());
                file.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void writeJsonToFileObject(JSONObject jsonObject, String fileName) {
        try {
            JSONParser parser = new JSONParser();

            try (FileReader reader = new FileReader(fileName)) {
            } catch (FileNotFoundException e) {
                jsonObject = new JSONObject();
            }


            try (FileWriter file = new FileWriter(fileName)) {
                file.write(jsonObject.toString());
                file.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String objectToString(JSONObject object) {
        return object.toString();
    }
    public Integer getKeyValue(String key,String filePath){
        JSONObject jsonObject=readJsonFileObject(filePath);
        try {
            return jsonObject.getInt(key);
        } catch (JSONException e) {
            return 0;
        }
    }
}
