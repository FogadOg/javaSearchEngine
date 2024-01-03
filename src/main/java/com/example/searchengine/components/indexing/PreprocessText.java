package com.example.searchengine.components.indexing;

import java.util.ArrayList;
import java.util.List;

public class PreprocessText {
    public String[] stopWords;

    public PreprocessText(){
        stopWords = new String[]{
                "call", "upon", "still", "nevertheless", "down", "every", "forty", "'re", "always", "whole", "side", "n't", "now", "however", "an", "show", "least", "give", "below", "did", "sometimes", "which", "'s", "nowhere", "per", "hereupon", "yours", "she", "moreover", "eight", "somewhere", "within", "whereby", "few", "has", "so", "have", "for", "noone", "top", "were", "those", "thence", "eleven", "after", "no", "’ll", "others", "ourselves", "themselves", "though", "that", "nor", "just", "’s", "before", "had", "toward", "another", "should", "herself", "and", "these", "such", "elsewhere", "further", "next", "indeed", "bottom", "anyone", "his", "each", "then", "both", "became", "third", "whom", "'ve", "mine", "take", "many", "anywhere", "to", "well", "thereafter", "besides", "almost", "front", "fifteen", "towards", "none", "be", "herein", "two", "using", "whatever", "please", "perhaps", "full", "ca", "we", "latterly", "here", "therefore", "us", "how", "was", "made", "the", "or", "may", "’re", "'ve", "anyway", "amongst", "used", "ever", "of", "there", "than", "why", "really", "whither", "in", "only", "wherein", "last", "under", "own", "therein", "go", "seems", "'m", "wherever", "either", "someone", "up", "doing", "on", "rather", "ours", "again", "same", "over", "'s", "latter", "during", "done", "'re", "put", "'m", "much", "neither", "among", "seemed", "into", "once", "my", "otherwise", "part", "everywhere", "never", "myself", "must", "will", "am", "can", "else", "although", "as", "beyond", "are", "too", "becomes", "does", "a", "everyone", "but", "some", "regarding", "‘ll", "against", "throughout", "yourselves", "him", "'d", "it", "himself", "whether", "move", "’m", "hereafter", "re", "while", "whoever", "your", "first", "amount", "twelve", "serious", "other", "any", "off", "seeming", "four", "itself", "nothing", "beforehand", "make", "out", "very", "already", "various", "until", "hers", "they", "not", "them", "where", "would", "since", "everything", "at", "together", "yet", "more", "six", "back", "with", "thereupon", "becoming", "around", "due", "keep", "somehow", "n‘t", "across", "all", "when", "i", "empty", "nine", "five", "get", "see", "been", "name", "between", "hence", "ten", "several", "from", "whereupon", "through", "hereby", "'ll", "alone", "something", "formerly", "without", "above", "onto", "except", "enough", "become", "behind", "’d", "its", "most", "n’t", "might", "whereas", "anything", "if", "her", "via", "fifty", "is", "thereby", "twenty", "often", "whereafter", "their", "also", "anyhow", "cannot", "our", "could", "because", "who", "beside", "by", "whence", "being", "meanwhile", "this", "afterwards", "whenever", "mostly", "what", "one", "nobody", "seem", "less", "do", "'d", "say", "thus", "unless", "along", "yourself", "former", "thru", "he", "hundred", "three", "sixty", "me", "sometime", "whose", "you", "quite", "’ve", "about", "even",
                "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "you're", "you've", "you'll", "you'd", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "she's", "her", "hers", "herself", "it", "it's", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "that'll", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "don't", "should", "should've", "now", "d", "ll", "m", "o", "re", "ve", "y", "ain", "aren", "aren't", "couldn", "couldn't", "didn", "didn't", "doesn", "doesn't", "hadn", "hadn't", "hasn", "hasn't", "haven", "haven't", "isn", "isn't", "ma", "mightn", "mightn't", "mustn", "mustn't", "needn", "needn't", "shan", "shan't", "shouldn", "shouldn't", "wasn", "wasn't", "weren", "weren't", "won", "won't", "wouldn", "wouldn't",
                "those", "on", "own", "’ve", "yourselves", "around", "between", "four", "been", "alone", "off", "am", "then", "other", "can", "regarding", "hereafter", "front", "too", "used", "wherein", "‘ll", "doing", "everything", "up", "onto", "never", "either", "how", "before", "anyway", "since", "through", "amount", "now", "he", "was", "have", "into", "because", "not", "therefore", "they", "n’t", "even", "whom", "it", "see", "somewhere", "thereupon", "nothing", "whereas", "much", "whenever", "seem", "until", "whereby", "at", "also", "some", "last", "than", "get", "already", "our", "once", "will", "noone", "'m", "that", "what", "thus", "no", "myself", "out", "next", "whatever", "although", "though", "which", "would", "therein", "nor", "somehow", "whereupon", "besides", "whoever", "ourselves", "few", "did", "third", "anything", "twelve", "against", "while", "twenty", "if", "however", "herself", "when", "may", "ours", "six", "done", "seems", "else", "call", "perhaps", "had", "nevertheless", "where", "otherwise", "still", "within", "its", "for", "together", "elsewhere", "throughout", "of", "others", "show", "’s", "anywhere", "anyhow", "as", "are", "the", "hence", "something", "hereby", "nowhere", "latterly", "say", "does", "neither", "his", "go", "forty", "put", "their", "by", "namely", "could", "five", "unless", "itself", "is", "nine", "whereafter", "down", "bottom", "thereby", "such", "both", "she", "become", "whole", "who", "yourself", "every", "thru", "except", "very", "several", "among", "being", "be", "mine", "further", "n‘t", "here", "during", "why", "with", "just", "'s", "becomes", "’ll", "about", "a", "using", "seeming", "'d", "'ll", "'re", "due", "wherever", "beforehand", "fifty", "becoming", "might", "amongst", "my", "empty", "thence", "thereafter", "almost", "least", "someone", "often", "from", "keep", "him", "or", "‘m", "top", "her", "nobody", "sometime", "across", "‘s", "’re", "hundred", "only", "via", "name", "eight", "three", "back", "to", "all", "became", "move", "me", "we", "formerly", "so", "i", "whence", "under", "always", "himself", "in", "herein", "more", "after", "themselves", "you", "them", "above", "sixty", "hasnt", "your", "made", "indeed", "most", "everywhere", "fifteen", "but", "must", "along", "beside", "hers", "side", "former", "full", "anyone", "has", "yours", "whose", "behind", "please", "amoungst", "mill", "ten", "seemed", "sometimes", "should", "over", "take", "each", "don", "same", "rather", "really", "latter", "and", "hereupon", "part", "per", "eleven", "ever", "enough", "again", "us", "yet", "moreover", "mostly", "one", "meanwhile", "whither", "there", "toward", "give", "system", "do", "quite", "an", "these", "everyone", "towards", "this", "bill", "cannot", "un", "afterwards", "beyond", "were", "whether", "well", "another", "below", "first", "upon", "any", "none", "many", "various", "serious", "re", "two", "less", "couldnt"

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

    public String processString(String text){
        return removePunctuation(text).toLowerCase();
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
