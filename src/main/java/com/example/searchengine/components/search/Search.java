package com.example.searchengine.components.search;

import com.example.searchengine.components.nGram.NGram;
import com.example.searchengine.components.stemmer.Stemmer;
import org.json.JSONTokener;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
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