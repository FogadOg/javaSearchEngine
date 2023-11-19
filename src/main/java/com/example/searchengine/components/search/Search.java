package com.example.searchengine.components.search;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search {
    public String searchTerm;
    public String dataFile="data.json";

    public Search(String searchTrem){
        this.searchTerm=searchTrem;
    }

    public List<String> getSearchResults(){
        JSONParser jsonParser = new JSONParser();
        try{
            JSONArray jsonDataArray = (JSONArray) jsonParser.parse(new FileReader(dataFile));

            for(Object urlData:jsonDataArray){
                JSONObject urlDataObject=(JSONObject) urlData;


                System.out.println(urlDataObject.get("url"));

            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<String>();
    }

    public static void main(String[] args){

    }



}
