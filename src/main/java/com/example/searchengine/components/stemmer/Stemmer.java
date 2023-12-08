package com.example.searchengine.components.stemmer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stemmer {
    private List<String> prefixes;
    private List<String> suffixes;

    public Stemmer(){
        this.prefixes= Arrays.asList(
                "anti","auto","bio","circum","co","con","de","dis","en","ex","extra","fore",
                "hyper","il","im","in","inter","intra","ir","marco","micro","mid","mis","multi",
                "non","ob","omni","out","over","para","peri","poly","post","pre","pro","pseudo",
                "re","semi","sub","super","syn","tele","trans","tri","ultra","un","under","uni"
        );
        this.suffixes= Arrays.asList(
                "ee","eer","er","ion","ism","ity","ment","ness","or","sion","ship","th",
                "able","ible","al","ant","ary","ful","ic","ious","ous","ive","less","y"
                ,"ed","en","er","ing","ize","ise","ly","ward","wise"
        );

    }

    public String stemString(String string){
        String stemmedString="";
        String[] splitString=string.split(" ");

        for (String word : splitString){
            for(String suffix : suffixes){

                String stemmedWord=removeSuffix(word, suffix);
                if(!stemmedWord.isEmpty()){
                    stemmedString+=removePrefix(stemmedWord)+" ";
                }

            }
        }

        return stemmedString;
    }

    private String removeSuffix(String word, String suffix){
        if (word.length()>suffix.length()){
            String lastCharsOfString= word.substring(word.length() - suffix.length());
            if(lastCharsOfString.equals(suffix)){
                return word.substring(0, word.length() - suffix.length());
            }
        }
        return "";
    }

    private String removePrefix(String word){
        for(String prefix: prefixes ){
            if (word.length()>prefix.length()){
                String firstCharsOfString= word.substring(0,prefix.length());
                if(firstCharsOfString.equals(prefix)){
                    return word.substring(prefix.length());
                }
            }
        }

        return "";
    }




}
