package com.example.searchengine.components.crawler;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CrawlerService {

    public boolean checkIfPageInJsonFile(String url, String jsonFile){
        JSONParser jsonParser = new JSONParser();

        try{
            JSONArray jsonDataArray = (JSONArray) jsonParser.parse(new FileReader(jsonFile));

            for(Object urlData:jsonDataArray){
                JSONObject urlDataObject=(JSONObject) urlData;
                if(urlDataObject.get("url").equals(url)){
                    return true;
                }
            }
            return false;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args){

    }
}
