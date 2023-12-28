package com.example.searchengine.components.tfIdf;

import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

public class TfIdfVector {

    private final List<Float> vectorValues=new ArrayList<>();

    public TfIdfVector multiplyVectors(TfIdfVector otherVector){
        TfIdfVector resultVector=new TfIdfVector();

        int vector1Size=vectorValues.size();
        int vector2Size=otherVector.vectorValues.size();

        if (vector1Size!=vector2Size){
            String errorMessage=String.format("vector 1 and vector2 must be same size, got vector1: %1$s vector2: %2$s", vector1Size, vector2Size);
            throw new IllegalArgumentException(errorMessage);
        }

        for(int i=0;i<vector1Size;i++){
            Float sum= vectorValues.get(i) * otherVector.vectorValues.get(i);

            resultVector.addToVector(sum);
        }

        return resultVector;

    }
    public void addToVector(Float value){
        vectorValues.add(value);
    }
    public List<Float> getVector(){
        return vectorValues;
    }

    public Double dotProduct(TfIdfVector otherVector){
        double sum=0.;
        for(int i=0; i<vectorValues.size();i++){
            sum+= vectorValues.get(i) * otherVector.vectorValues.get(i);
        }

        return sum;
    }

    public Double magnitute(){
        double sum=0.0;

        for(double value: vectorValues){
            sum+=value*value;
        }

        return Math.sqrt(sum);
    }

    public Double cosineSimilarity(TfIdfVector otherVector){
        Double dotProduct=dotProduct(otherVector);

        Double magnituteA=magnitute();
        Double magnituteB=otherVector.magnitute();

        if(magnituteA==0||magnituteB==0){
            return 0.0;
        }

        return dotProduct/(magnituteA*magnituteB);
    }


}
