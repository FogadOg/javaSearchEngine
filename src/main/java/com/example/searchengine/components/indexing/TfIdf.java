package com.example.searchengine.components.indexing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import static org.apache.coyote.http11.Constants.a;

public class TfIdf {

    public PreprocessText preprocesser= new PreprocessText();

    public Float tf(String term, Hashtable<String, Integer> documentTermCount){



        int termsSum = 0;
        Enumeration<Integer> values = documentTermCount.elements();
        while (values.hasMoreElements()) {
            termsSum += values.nextElement();
        }

        if(documentTermCount.get(term)==null){
            return (float) 0/termsSum;

        }

        return (float) documentTermCount.get(term)/termsSum;
    }
    public Integer idf(String term){
        Integer documentsWithTerm=countDocumentWithTerm(term);
        Integer numberOfDocument=10;

        return (Integer) (int) Math.log(documentsWithTerm*numberOfDocument);

    }

    private Integer countDocumentWithTerm(String term){
        JSONParser parser = new JSONParser();

        Integer termAccurance=0;

        try{
            JSONArray websites = (JSONArray) parser.parse(new FileReader("data.json"));

            for (Object websiteData : websites)
            {
                JSONObject website = (JSONObject) websiteData;
                termAccurance+=isTermPresent(term, website);


            }

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("termAccurance: "+termAccurance);
        return termAccurance;
    }

    private Integer isTermPresent(String term,JSONObject website){
        JSONArray websiteContent= (JSONArray) website.get("content");

        for(Object p: websiteContent){
            String text = (String) p;

            List<String> terms=preprocesser.process(text);

            for(String termInDocument: terms){
                if (termInDocument.trim().equals(term)){
                    return 1;
                }

            }

        }
        return 0;
    }

    public Float tfIdf(String term, Hashtable<String, Integer> documentTermCount){

        Float tf = tf(term, documentTermCount);

        Integer idf = idf(term);

        return tf*idf;
    }

    public Hashtable<String, Integer> countTerms(List<String> terms){
        Hashtable<String, Integer> termFrequency = new Hashtable<>();

        for (String term : terms) {
            if (!termFrequency.containsKey(term)) {
                termFrequency.put(term, 1);
            } else {
                int currentTermFrequency = termFrequency.get(term);
                termFrequency.put(term, currentTermFrequency + 1);
            }
        }

        return termFrequency;
    }
}