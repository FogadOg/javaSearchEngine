package com.example.searchengine.components.search;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Search {
    public String searchTerm;
    public String dataFile="data.json";

    public Search(String searchTrem){
        this.searchTerm=searchTrem;
    }

    public void getSearchResults(){


    }

    public static void main(String[] args){

    }



}
