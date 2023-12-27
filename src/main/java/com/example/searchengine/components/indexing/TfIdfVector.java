package com.example.searchengine.components.indexing;

import java.util.ArrayList;
import java.util.List;

public class TfIdfVector {

    public List<Float> vectorValues=new ArrayList<>();

    public TfIdfVector multiplyVectors(TfIdfVector vector2){
        TfIdfVector resultVector=new TfIdfVector();

        int vector1Size=vectorValues.size();
        int vector2Size=vector2.vectorValues.size();

        if (vector1Size!=vector2Size){
            String errorMessage=String.format("vector 1 and vector2 must be same size, got vector1: %1$s vector2: %2$s", vector1Size, vector2Size);
            throw new IllegalArgumentException(errorMessage);
        }

        for(int i=0;i<vector1Size;i++){
            Float sum= vectorValues.get(i) * vector2.vectorValues.get(i);

            resultVector.addToVector(sum);
        }

        return resultVector;

    }

    public void addToVector(Float value){
        vectorValues.add(value);
    }
}
