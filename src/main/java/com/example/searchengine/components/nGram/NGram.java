package com.example.searchengine.components.nGram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


}
