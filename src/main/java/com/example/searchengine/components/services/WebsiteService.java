package com.example.searchengine.components.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class WebsiteService {
    public JSONObject replaceField(JSONObject jsonObject,String field, String newString){
        return jsonObject.put(field, newString);
    }

    public JSONObject replaceField(JSONObject jsonObject,String field, JSONObject newObject){
        return jsonObject.put(field, newObject);
    }
    public JSONObject replaceField(JSONObject jsonObject, String field, JSONArray newArray){
        return jsonObject.put(field, newArray);
    }
}
