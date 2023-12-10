package com.example.searchengine.components.nGram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NGram {

    public List<String> getNGram(int n,String string){
        List<String> nGrams=new ArrayList<String>();

        String[] wordToLoop = string.split(" ");
        for(int i=0; i < wordToLoop.length-n+1; i++){
            String word= Arrays.toString(Arrays.copyOfRange(wordToLoop,i,i+n));
            nGrams.add(word);
        }



        return nGrams;
    }

    public Integer matchNGrams(List<String> nGram1, List<String> nGram2){
        Integer matches = 0;

        for(int nGram1Idx=0;nGram1Idx<nGram1.size();nGram1Idx++){
            for(int nGram2Idx=0;nGram2Idx<nGram2.size();nGram2Idx++){
                if (Objects.equals(nGram1.get(nGram1Idx), nGram2.get(nGram2Idx))){
                    matches+=1;
                }


            }
        }


        return matches;
    }
}
