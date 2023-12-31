package com.example.searchengine.components;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


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
    public static void writeJsonToFile(JSONObject jsonObject, String fileName) {
        try {
            JSONParser parser = new JSONParser();

            // Read the existing JSON array from the file
            JSONArray jsonArray;
            try (FileReader reader = new FileReader(fileName)) {
                Object obj = parser.parse(reader);
                if (obj instanceof JSONArray) {
                    jsonArray = (JSONArray) obj;
                } else {
                    jsonArray = new JSONArray();
                }
            } catch (FileNotFoundException e) {
                jsonArray = new JSONArray(); // Create a new array if the file doesn't exist
            }

            // Append the new object to the existing JSON array
            jsonArray.put(jsonObject);

            // Write the entire updated JSON array back to the file
            try (FileWriter file = new FileWriter(fileName)) {
                file.write(jsonArray.toString());
                file.flush();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void appendObjectToFile(JSONObject jsonObject,String filePath) {
        try {
            JSONArray jsonArray;

            try {
                // Read the existing JSON array from the file
                String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
                jsonArray = new JSONArray(fileContent);
            } catch (IOException | JSONException e) {
                jsonArray = new JSONArray(); // Create a new array if the file doesn't exist or is invalid JSON
            }

            // Append the new object to the existing JSON array
            jsonArray.put(jsonObject);

            // Write the updated JSON array back to the file
            try (FileWriter file = new FileWriter(filePath)) {
                file.write(jsonArray.toString());
                file.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String objectToString(JSONObject object) {
        return object.toString();
    }
}
