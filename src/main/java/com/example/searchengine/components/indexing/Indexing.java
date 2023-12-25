package com.example.searchengine.components.indexing;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Indexing {
    public void main(){
        TfIdf tfIdf = new TfIdf();

        PreprocessText preprocesser= new PreprocessText();
        List<String> processedText=preprocesser.process("Removing stopwords depends on case: You can remove in cases cases cases cases cases cases cases cases cases cases like Text classification like Email filtering, Organizing corporate documents, or Sentiment Analysis etc.. You should avoid stopwords removal when doing Text summarization or building Ques-Answer engine, or in language modelling..");
        Hashtable<String, Integer> tremFrequancy = tfIdf.countTerms(processedText);


        tfIdf.tfIdf("data",tremFrequancy);

    }

}
