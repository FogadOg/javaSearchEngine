package com.example.searchengine.components.indexing;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class TfIdf {

    public Float tf(String term, Hashtable<String, Integer> documentTermCount){



        int termsSum = 0;
        Enumeration<Integer> values = documentTermCount.elements();
        while (values.hasMoreElements()) {
            termsSum += values.nextElement();
        }
        Float termFrequency= (float) documentTermCount.get(term)/termsSum;

        return termFrequency;
    }
    public void idf(String term, Hashtable<String, Integer> documentTermCount){
        
    }

    public void tfIdf(){

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
