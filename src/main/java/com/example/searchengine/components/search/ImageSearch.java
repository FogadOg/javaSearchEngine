package com.example.searchengine.components.search;

import com.example.searchengine.components.stemmer.Stemmer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ImageSearch extends Search{


    public String filePath="data.json";

    public Stemmer stemmer=new Stemmer();
    public JSONArray getAllImages(String searchTerm){
        String stemmedSearchTrem=stemmer.stemString(searchTerm);

        return searchForRelevantImages(stemmedSearchTrem);

    }

    private JSONArray searchForRelevantImages(String stemmedSearchTrem){
        JSONArray proccessedImages = new JSONArray();
        JSONParser parser = new JSONParser();

        try{
            JSONArray a = (JSONArray) parser.parse(new FileReader(filePath));

            for (Object o : a)
            {

                JSONObject website = (JSONObject) o;

                JSONObject jsonObject = new JSONObject();

                JSONArray imagesArray = (JSONArray) website.get("images");


                for(Object image: imagesArray){
                    Integer rating=rateImage();
                    System.out.println("image: "+image);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return proccessedImages;
    }

    private Integer rateImage(){
        Integer initialRating=0;


        return initialRating;
    }

}
