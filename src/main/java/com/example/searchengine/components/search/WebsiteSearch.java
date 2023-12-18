package com.example.searchengine.components.search;

import com.example.searchengine.components.stemmer.Stemmer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class WebsiteSearch extends Search{

    public String filePath="data.json";

    public Stemmer stemmer=new Stemmer();
    public JSONArray getAllWebsites(String searchTerm){
        String stemmedSearchTrem=stemmer.stemString(searchTerm);

        return searchForRelevantWebsites(stemmedSearchTrem);

    }

    private JSONArray searchForRelevantWebsites(String stemmedSearchTrem){
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

}
