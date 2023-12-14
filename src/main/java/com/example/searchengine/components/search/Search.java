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

        try{
            JSONArray a = (JSONArray) parser.parse(new FileReader(filePath));

            for (Object o : a)
            {

                JSONObject website = (JSONObject) o;

                JSONObject jsonObject = new JSONObject();

                int[] nGrams={1,2,3,4,5};
                Integer nGramPoints=checkForNGramRepet(nGrams,stemmedSearchTrem,website.get("content").toString());
 

                Integer websiteRating=Integer.parseInt(website.get("rating").toString());


                jsonObject.put("url", website.get("url"));

                jsonObject.put("pageTitle", website.get("pageTitle"));
                jsonObject.put("pageName", website.get("pageName"));
                jsonObject.put("favicon", website.get("favicon"));
                jsonObject.put("rating", websiteRating+nGramPoints);
                jsonObject.put("lastTimeCrawled", website.get("lastTimeCrawled"));
                jsonObject.put("images", website.get("images"));

                jsonArray.add(jsonObject);


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonArray;
    }

    private Integer checkForNGramRepet(int[] nGrams, String stemmedSearchTrem, String content){
        Integer nGramPoints=0;
        NGram nGram=new NGram();

        for(int nGramNumber : nGrams){
            List<String> contentNGram=nGram.getNGram(nGramNumber,content);
            List<String> searchNGram=nGram.getNGram(nGramNumber,stemmedSearchTrem);

            nGramPoints+=nGram.matchNGrams(contentNGram, searchNGram);
        }
        return nGramPoints;
    }



}