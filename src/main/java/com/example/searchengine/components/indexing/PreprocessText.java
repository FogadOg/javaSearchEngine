package com.example.searchengine.components.indexing;

import java.util.ArrayList;
import java.util.List;

public class PreprocessText {
    public String[] stopWords;

    public PreprocessText(){
        stopWords = new String[]{
                "call", "upon", "still", "nevertheless", "down", "every", "forty", "'re", "always", "whole", "side", "n't", "now", "however", "an", "show", "least", "give", "below", "did", "sometimes", "which", "'s", "nowhere", "per", "hereupon", "yours", "she", "moreover", "eight", "somewhere", "within", "whereby", "few", "has", "so", "have", "for", "noone", "top", "were", "those", "thence", "eleven", "after", "no", "’ll", "others", "ourselves", "themselves", "though", "that", "nor", "just", "’s", "before", "had", "toward", "another", "should", "herself", "and", "these", "such", "elsewhere", "further", "next", "indeed", "bottom", "anyone", "his", "each", "then", "both", "became", "third", "whom", "'ve", "mine", "take", "many", "anywhere", "to", "well", "thereafter", "besides", "almost", "front", "fifteen", "towards", "none", "be", "herein", "two", "using", "whatever", "please", "perhaps", "full", "ca", "we", "latterly", "here", "therefore", "us", "how", "was", "made", "the", "or", "may", "’re", "'ve", "anyway", "amongst", "used", "ever", "of", "there", "than", "why", "really", "whither", "in", "only", "wherein", "last", "under", "own", "therein", "go", "seems", "'m", "wherever", "either", "someone", "up", "doing", "on", "rather", "ours", "again", "same", "over", "'s", "latter", "during", "done", "'re", "put", "'m", "much", "neither", "among", "seemed", "into", "once", "my", "otherwise", "part", "everywhere", "never", "myself", "must", "will", "am", "can", "else", "although", "as", "beyond", "are", "too", "becomes", "does", "a", "everyone", "but", "some", "regarding", "‘ll", "against", "throughout", "yourselves", "him", "'d", "it", "himself", "whether", "move", "’m", "hereafter", "re", "while", "whoever", "your", "first", "amount", "twelve", "serious", "other", "any", "off", "seeming", "four", "itself", "nothing", "beforehand", "make", "out", "very", "already", "various", "until", "hers", "they", "not", "them", "where", "would", "since", "everything", "at", "together", "yet", "more", "six", "back", "with", "thereupon", "becoming", "around", "due", "keep", "somehow", "n‘t", "across", "all", "when", "i", "empty", "nine", "five", "get", "see", "been", "name", "between", "hence", "ten", "several", "from", "whereupon", "through", "hereby", "'ll", "alone", "something", "formerly", "without", "above", "onto", "except", "enough", "become", "behind", "’d", "its", "most", "n’t", "might", "whereas", "anything", "if", "her", "via", "fifty", "is", "thereby", "twenty", "often", "whereafter", "their", "also", "anyhow", "cannot", "our", "could", "because", "who", "beside", "by", "whence", "being", "meanwhile", "this", "afterwards", "whenever", "mostly", "what", "one", "nobody", "seem", "less", "do", "'d", "say", "thus", "unless", "along", "yourself", "former", "thru", "he", "hundred", "three", "sixty", "me", "sometime", "whose", "you", "quite", "’ve", "about", "even",
                "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "you're", "you've", "you'll", "you'd", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "she's", "her", "hers", "herself", "it", "it's", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "that'll", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "don't", "should", "should've", "now", "d", "ll", "m", "o", "re", "ve", "y", "ain", "aren", "aren't", "couldn", "couldn't", "didn", "didn't", "doesn", "doesn't", "hadn", "hadn't", "hasn", "hasn't", "haven", "haven't", "isn", "isn't", "ma", "mightn", "mightn't", "mustn", "mustn't", "needn", "needn't", "shan", "shan't", "shouldn", "shouldn't", "wasn", "wasn't", "weren", "weren't", "won", "won't", "wouldn", "wouldn't"
        };
    }

    public List<String> processForIndexing(String text){
        String textWithoutPunctuation=removePunctuation(text).toLowerCase();
        String[] splitString=textWithoutPunctuation.split(" ");

        List<String> processedString=new ArrayList<>();


        for(int i=0; splitString.length>i;i++){
            if(!isStopWord(splitString[i])){
                processedString.add(splitString[i]);
            }
        }

        return processedString;
    }

    private String removePunctuation(String text){
        return text.replaceAll("\\p{Punct}", "");
    }

    private Boolean isStopWord(String word){
        for(String stopWord: stopWords){
            if(stopWord.equals(word)){
                return true;
            }

        }
        return false;
    }

}
