package com.example.searchengine.components.search;

import com.example.searchengine.components.stemmer.Stemmer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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


                for(Object imageObject: imagesArray){
                    JSONObject jsonImageObject = (JSONObject) imageObject;
                    Integer rating=rateImage(jsonImageObject, stemmedSearchTrem);
                    jsonImageObject.put("rating",rating);

                    proccessedImages.add(jsonImageObject);

                }

            }
            sortArray(proccessedImages);
        }catch (Exception e){
            e.printStackTrace();
        }
        return proccessedImages;
    }

    private void sortArray(JSONArray JsonArray){

        Collections.sort(JsonArray, (o1, o2) -> {
            JSONObject obj1 = (JSONObject) o1;
            JSONObject obj2 = (JSONObject) o2;

            Integer rating1 = Integer.parseInt(obj1.get("rating").toString());
            Integer rating2 = Integer.parseInt(obj2.get("rating").toString());


            return rating2.compareTo(rating1);
        });
    }

    private Integer rateImage(JSONObject imageObject,String stemmedSearchTrem){
        Integer initialRating=10;

        String imageAlt=imageObject.get("alt").toString();
        if(imageAlt.trim().isEmpty()){
            initialRating-=5;
        }

        String stemmedAltAttribute=stemmer.stemString(imageAlt);
        int[] nGrams={1,2,3,4,5};
        Integer nGramPoints=checkForNGramRepet(nGrams,stemmedSearchTrem,stemmedAltAttribute);

        initialRating+=nGramPoints;


        return initialRating;
    }

}
