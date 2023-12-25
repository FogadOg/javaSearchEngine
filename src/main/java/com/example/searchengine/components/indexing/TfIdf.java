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
    public Integer idf(String term){
        Integer documentsWithTerm=countDocumentWithTerm(term);
        Integer numberOfDocument=10;

        return (Integer) (int) Math.log(documentsWithTerm*numberOfDocument);

    }

    private Integer countDocumentWithTerm(String term){


        return 1;
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
