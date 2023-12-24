package com.example.searchengine.components.indexing;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Indexing {
    public void main(){
        PreprocessText preprocesser= new PreprocessText();
        List<String> processedText=preprocesser.process("Removing stopwords depends on case: You can remove in cases like Text classification like Email filtering, Organizing corporate documents, or Sentiment Analysis etc.. You should avoid stopwords removal when doing Text summarization or building Ques-Answer engine, or in language modelling..");
        Hashtable<String, Integer> tremFrequancy = countTerms(processedText);

        System.out.println("tremFrequancy: "+tremFrequancy);

    }

    private Hashtable<String, Integer> countTerms(List<String> terms){
        Hashtable<String, Integer> termFrequency=new Hashtable<>();

        for(String term: terms){
            if(!termFrequency.containsValue(term)){
                termFrequency.put(term, 1);
                continue;
            }
            int currentTremsFrequancy=termFrequency.get(term);
            termFrequency.put(term, currentTremsFrequancy + 1);
            termFrequency.put(term, 1);
        }

        return termFrequency;
    }


}
