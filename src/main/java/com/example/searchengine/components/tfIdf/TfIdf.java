package com.example.searchengine.components.indexing;

import com.example.searchengine.components.tfIdf.TfIdfVector;
import com.google.gson.*;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

public class TfIdf {

    public PreprocessText preprocesser= new PreprocessText();

    public Double tf(String term, Hashtable<String, Integer> documentTermCount){



        int termsSum = 0;
        Enumeration<Integer> values = documentTermCount.elements();
        while (values.hasMoreElements()) {
            termsSum += values.nextElement();
        }

        if(documentTermCount.get(term)==null) return (double) 0/termsSum;



        return (double) documentTermCount.get(term)/termsSum;
    }

    public Integer idf(String term){
        Integer documentsWithTerm=countDocumentWithTerm(term);
        Integer numberOfDocument=getNumberOfDocuments();

        return (Integer) (int) Math.log(documentsWithTerm*numberOfDocument);

    }

    private Integer countDocumentWithTerm(String term){
        JSONParser parser = new JSONParser();

        int termAccurance=0;

        try{
            JSONArray documents = (JSONArray) parser.parse(new FileReader("data.json"));
            for (Object websiteData : documents)
            {
                JSONObject website = (JSONObject) websiteData;
                termAccurance+=isTermPresent(term, website);


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return termAccurance;
    }

    private Integer getNumberOfDocuments(){
        JSONParser parser = new JSONParser();

        int numberOfDocument=0;

        try{
            JSONArray documents = (JSONArray) parser.parse(new FileReader("data.json"));
            numberOfDocument+=documents.size();

        }catch (Exception e){
            e.printStackTrace();
        }
        return numberOfDocument;
    }

    private Integer isTermPresent(String term,JSONObject website){
        JSONArray websiteContent= (JSONArray) website.get("content");

        for(Object p: websiteContent){
            String text = (String) p;

            List<String> terms=preprocesser.processForIndexing(text);

            for(String termInDocument: terms){
                if (termInDocument.trim().equals(term)){
                    return 1;
                }

            }

        }
        return 0;
    }

    public Double tfIdf(String term, Hashtable<String, Integer> documentTermCount){
        Double tf = tf(term, documentTermCount);
        Integer idf = idf(term);

        return tf*idf;
    }


    public Hashtable<String, Integer> countTerms(List<String> terms){
        Hashtable<String, Integer> termFrequency = new Hashtable<>();

        for (String term : terms) {
            if(!term.isEmpty()){
                if (!termFrequency.containsKey(term)) {
                    termFrequency.put(term, 1);
                } else {
                    int currentTermFrequency = termFrequency.get(term);
                    termFrequency.put(term, currentTermFrequency + 1);
                }
            }

        }

        return termFrequency;
    }

    public TfIdfVector tfIdfVector(List<String> termsVector, Hashtable<String, Integer> overallTremFrequancy){
        TfIdfVector vector=new TfIdfVector();

        for(String term: termsVector){
            Double tfIdf = tfIdf(term, overallTremFrequancy);
            vector.addToVector(tfIdf);
        }

        return vector;


    }

    public TfIdfVector getVector(String documet1, String document2){
        List<String> splitSearchQuery=preprocesser.processForIndexing(documet1);
        Hashtable<String, Integer> termFrequancy=countTerms(preprocesser.processForIndexing(document2));

        return tfIdfVector(splitSearchQuery,termFrequancy);

    }

}
