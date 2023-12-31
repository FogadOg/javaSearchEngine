package com.example.searchengine.components.indexing;
import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.indexing.TfIdf;
import com.google.gson.*;
import org.apache.xpath.operations.Bool;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Indexing {
    public String jsonFilePath;

    JsonFileService jsonFileService = new JsonFileService();
    public Indexing(String jsonFilePath){
        this.jsonFilePath=jsonFilePath;

    }

    public void mapDocument(String documentText,String documentId, Double termThreshHold){
        TfIdf tfIdf = new TfIdf();
        PreprocessText preprocessor= new PreprocessText();

        List<String> termsList=preprocessor.processForIndexing(documentText);
        Hashtable<String, Integer> termFrequancy = tfIdf.countTerms(termsList);

        System.out.println("indexing: "+documentId);
        for(String term: termsList){
            Float tfIdfScore=tfIdf.tfIdf(term,termFrequancy);
            addDocument(tfIdfScore, termThreshHold, term, documentId);

        }

    }

    private void addDocument(Float tfIdfScore, Double termThreshHold, String term, String documentId){
        if(tfIdfScore>=termThreshHold){
            try {
                FileReader reader = new FileReader(jsonFilePath);
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

                JsonObject targetObject = findObjectWithKey(jsonArray, term);

                if (targetObject != null) {
                    JsonArray termArray = (JsonArray) targetObject.get(term);

                    Boolean isDocumentPresent=isDocumentAllReadyAssociatedWithTerm(documentId, termArray);


                    if(!isDocumentPresent){
                        JsonArray array = targetObject.getAsJsonArray(term);

                        JsonObject newObject = new JsonObject();
                        newObject.addProperty("id", documentId);

                        array.add(newObject);

                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        FileWriter writer = new FileWriter(jsonFilePath);
                        gson.toJson(jsonArray, writer);
                        writer.close();
                    }
                }else{
                    addTermToIndexMap(term);
                    addDocument(tfIdfScore, termThreshHold, term, documentId);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean isDocumentAllReadyAssociatedWithTerm(String documentId,JsonArray termArray){
        for(JsonElement object: termArray){
            JsonObject jsonObject = object.getAsJsonObject();

            if(jsonObject.get("id").getAsString().equals(documentId)){
                return true;
            }

        }
        return false;
    }

    private static JsonObject findObjectWithKey(JsonArray jsonArray, String keyToFind) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject obj = jsonArray.get(i).getAsJsonObject();
            if (obj.has(keyToFind)) {
                return obj;
            }
        }
        return null;
    }

    private void addTermToIndexMap(String term){
        JSONObject newObject = new JSONObject();
        JSONArray array=new JSONArray();
        newObject.put(term, array);
        
        JsonFileService.appendObjectToFile(newObject, jsonFilePath);

    }

    public JSONArray getTermMapping(String term){
        JSONArray returnJsonArray = new JSONArray();

        JSONArray jsonArray = jsonFileService.readJsonFile("indexMap.json");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            if (jsonObject.has(term)) {
                JSONArray termJsonArray = jsonObject.getJSONArray(term);

                for (int j = 0; j < termJsonArray.length(); j++) {
                    returnJsonArray.put(termJsonArray.get(j));
                }
            }
        }

        return returnJsonArray;
    }

}
