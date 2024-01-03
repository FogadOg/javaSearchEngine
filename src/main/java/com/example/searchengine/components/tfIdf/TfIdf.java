package com.example.searchengine.components.indexing;

import com.example.searchengine.components.JsonFileService;
import com.example.searchengine.components.tfIdf.TfIdfService;
import com.example.searchengine.components.tfIdf.TfIdfVector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class TfIdf {

    public PreprocessText preprocessor = new PreprocessText();

    public JsonFileService jsonFileService=new JsonFileService();

    TfIdfService tfIdfService = new TfIdfService();

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
        Integer documentsWithTerm=tfIdfService.getTermIdf(term);
        Integer numberOfDocument=getNumberOfDocuments();

        return (Integer) (int) Math.log(documentsWithTerm*numberOfDocument);

    }


    private Integer getNumberOfDocuments() {
        return jsonFileService.readJsonFile("data.json").length();
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

    public TfIdfVector tfIdfVector(List<String> termsVector, Hashtable<String, Integer> overallTermFrequency){
        TfIdfVector vector=new TfIdfVector();

        for(String term: termsVector){
            Double tfIdf = tfIdf(term, overallTermFrequency);
            vector.addToVector(tfIdf);
        }

        return vector;


    }

    public TfIdfVector getVector(String document1, String document2){
        List<String> splitSearchQuery= preprocessor.processForIndexing(document1);
        Hashtable<String, Integer> termFrequency=countTerms(preprocessor.processForIndexing(document2));

        return tfIdfVector(splitSearchQuery,termFrequency);

    }

}
