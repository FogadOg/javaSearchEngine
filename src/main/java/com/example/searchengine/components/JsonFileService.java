package com.example.searchengine.components;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
public class JsonFileService {


    public JSONArray readJsonFile(String filePath){
        JSONParser jsonParser = new JSONParser();
        JSONArray emptyJSONArray= new JSONArray();

        try {
            return (JSONArray) jsonParser.parse(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return emptyJSONArray;
    }
    public Object findObject(JSONArray jsonDataArray, String url){
        for (Object obj : jsonDataArray) {
            JSONObject website = (JSONObject) obj;

            String websiteUrl = (String) website.get("url");

            if (websiteUrl.equals(url)) {
                return website;
            }
        }
        return null;
    }

    public String objectToString(Object object){
        String turnToString=object.toString();
        String removeTwoLastAndFirstChars=turnToString.substring(2, turnToString.length()-2);
        return removeTwoLastAndFirstChars.trim();

    }
}
