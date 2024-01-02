package com.example.searchengine.components.tfIdf;

import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.indexing.PreprocessText;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class TfIdfService {
    static JsonFileService jsonFileService=new JsonFileService();
    static PreprocessText preprocessText=new PreprocessText();

    public static void incrementIdfCount(String text){
        String[] stringList=text.split(" ");
        Set<String> removeDuplicates=new HashSet<>(Arrays.asList(stringList));

        String[] x=removeDuplicates.toArray(new String[0]);


        for(String string: x){
            System.out.println("string: "+string);
            java.lang.String term = preprocessText.processString(java.lang.String.valueOf(string));
            updateKey(term);
        }

    }

    private static void updateKey(String term){
        JSONObject jsonObject=jsonFileService.readJsonFileObject("idf.Json");
        Integer idfCount=getTermIdf(term);
        jsonObject.put(term, idfCount+1);

        JsonFileService.writeJsonToFileObject(jsonObject, "idf.json");

    }

    public static Integer getTermIdf(String term){
        JSONObject jsonObject=jsonFileService.readJsonFileObject("idf.Json");
        try {
            return jsonObject.getInt(term);
        } catch (JSONException e) {
            return 0;
        }
    }

}
