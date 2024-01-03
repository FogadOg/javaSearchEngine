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
    JsonFileService jsonFileService=new JsonFileService();
    PreprocessText preprocessText=new PreprocessText();

    public void incrementIdfCount(String text){
        String[] stringList=text.split(" ");
        Set<String> removeDuplicates=new HashSet<>(Arrays.asList(stringList));

        String[] stringListWithOutDuplicates=removeDuplicates.toArray(new String[0]);

        for(String string: stringListWithOutDuplicates){
            java.lang.String term = preprocessText.processString(java.lang.String.valueOf(string));
            updateKey(term);
        }

    }

    private void updateKey(String term){
        JSONObject jsonObject=jsonFileService.readJsonFileObject("idf.Json");
        Integer idfCount=getTermIdf(term);
        jsonObject.put(term, idfCount+1);

        JsonFileService.writeJsonToFileObject(jsonObject, "idf.json");

    }

    public Integer getTermIdf(String term){
        JSONObject jsonObject=jsonFileService.readJsonFileObject("idf.Json");
        try {
            return jsonObject.getInt(term);
        } catch (JSONException e) {
            return 0;
        }
    }

}
