package com.example.searchengine.components.search;

import com.example.searchengine.components.NGram;

import java.util.List;

public class Search {

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



}