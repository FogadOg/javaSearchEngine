package com.example.searchengine.controller;

import com.example.searchengine.components.crawler.Crawler;
import com.example.searchengine.components.search.Search;
import com.example.searchengine.components.stemmer.Stemmer;
import com.google.gson.JsonArray;
import org.json.simple.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchPage {

    @CrossOrigin
    @GetMapping("/")
    public JSONArray home(){
        Crawler crwaler=new Crawler();
        crwaler.crawl();

        Search search = new Search();

        return search.getAllWebsites("");
    }
}
