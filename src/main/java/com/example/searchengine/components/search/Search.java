package com.example.searchengine.components.search;

import com.example.searchengine.components.nGram.NGram;
import com.example.searchengine.components.stemmer.Stemmer;
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Search {
    public String filePath="data.json";

    public JSONArray getAllWebsites(String searchTerm){
        Stemmer stemmer=new Stemmer();

        String stemmedSearchTrem=stemmer.stemString(searchTerm);

        return index(stemmedSearchTrem);

    }

    private JSONArray index(String stemmedSearchTrem){
        JSONArray jsonArray = new JSONArray();
        JSONParser parser = new JSONParser();
        NGram nGram=new NGram();

        try{
            JSONArray a = (JSONArray) parser.parse(new FileReader(filePath));

            for (Object o : a)
            {

                JSONObject website = (JSONObject) o;

                JSONObject jsonObject = new JSONObject();

                List<String> contentNGram=nGram.getNGram(2,website.get("content").toString());
                List<String> searchNGram=nGram.getNGram(2,stemmedSearchTrem);

                Integer matchedNGrams = nGram.matchNGrams(contentNGram, searchNGram);
                Integer websiteRating=Integer.parseInt(website.get("rating").toString());


                jsonObject.put("url", website.get("url"));

                jsonObject.put("pageTitle", website.get("pageTitle"));
                jsonObject.put("pageName", website.get("pageName"));
                jsonObject.put("favicon", website.get("favicon"));
                jsonObject.put("rating", websiteRating+matchedNGrams);
                jsonObject.put("lastTimeCrawled", website.get("lastTimeCrawled"));
                jsonObject.put("images", website.get("images"));

                jsonArray.add(jsonObject);


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }



}