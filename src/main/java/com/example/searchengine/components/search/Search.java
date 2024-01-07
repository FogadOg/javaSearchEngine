package com.example.searchengine.components.search;

import com.example.searchengine.components.textProcessers.NGram;
import com.example.searchengine.components.textProcessers.PreprocessText;
import com.example.searchengine.components.indexing.TfIdf;
import com.example.searchengine.components.tfIdf.TfIdfVector;

import java.util.List;

public class Search {

    public TfIdf tfIdf=new TfIdf();
    public PreprocessText preprocessText=new PreprocessText();

    public Integer checkForNGramRepet(int[] nGrams, String stemmedSearchTrem, String content){
        Integer nGramPoints=0;
        NGram nGram=new NGram();

        for(int nGramNumber : nGrams){
            List<String> contentNGram=nGram.getNGram(nGramNumber,content);
            List<String> searchNGram=nGram.getNGram(nGramNumber,stemmedSearchTrem);

            nGramPoints+=nGram.matchNGrams(contentNGram, searchNGram);
        }
        return nGramPoints;
    }



    public Double calculateTextsSimilarity(String searchQuery, String textToMatchWith){

        TfIdfVector v1=tfIdf.getVector(searchQuery,searchQuery);
        TfIdfVector v2=tfIdf.getVector(searchQuery,textToMatchWith);


        return v1.cosineSimilarity(v2);

    }





}