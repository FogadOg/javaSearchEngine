package com.example.searchengine.components.indexing;
import com.example.searchengine.components.indexing.TfIdf;
import com.google.gson.*;
import org.apache.xpath.operations.Bool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    public Indexing(String jsonFilePath){
        this.jsonFilePath=jsonFilePath;

    }

    public void mapDocument(String documentText,String documentId, Double termThreshHold){
        TfIdf tfIdf = new TfIdf();
        PreprocessText preprocesser= new PreprocessText();

        List<String> termsList=preprocesser.processForIndexing(documentText);
        Hashtable<String, Integer> tremFrequancy = tfIdf.countTerms(termsList);

        for(String term: termsList){
            Float tfIdfScore=tfIdf.tfIdf(term,tremFrequancy);
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

                    Boolean isDocumentPresent=isDocumentAllReadyAssociatedWithTrem(documentId, termArray);


                    if(!isDocumentPresent){
                        JsonArray array = targetObject.getAsJsonArray(term);

                        JsonObject newObject = new JsonObject();
                        newObject.addProperty("id", documentId); // Replace this with your object structure

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

    private Boolean isDocumentAllReadyAssociatedWithTrem(String documentId,JsonArray termArray){
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
        // Create a new JSON object to add
        JSONObject newObject = new JSONObject();
        JSONArray array=new JSONArray();
        newObject.put(term, array);


        // Write the JSON array back to the file
        writeJsonToFile(newObject, jsonFilePath);

    }
    private static void writeJsonToFile(JSONObject jsonObject, String fileName) {
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

            // Add the new object to the existing JSON array
            jsonArray.add(jsonObject);

            // Write the updated JSON array back to the file
            try (FileWriter file = new FileWriter(fileName)) {
                file.write(jsonArray.toJSONString());
                file.flush();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public JSONArray getTermMaping(String term){
        JSONParser parser = new JSONParser();
        JSONArray emptyArray=new JSONArray();
        try {
            FileReader reader = new FileReader(jsonFilePath);
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            JsonObject targetObject = findObjectWithKey(jsonArray, term);

            if (targetObject != null) {
                JSONArray termArray = (JSONArray) parser.parse(String.valueOf(targetObject.get(term)));
                return termArray;


            }else{
                return emptyArray;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return emptyArray;


    }

}
