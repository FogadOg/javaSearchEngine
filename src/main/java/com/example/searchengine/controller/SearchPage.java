package com.example.searchengine.controller;

import com.example.searchengine.components.crawler.Crawler;
import com.example.searchengine.components.search.Search;
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
    public void home(){
        Crawler crawler = new Crawler();
        crawler.crawl();
        //Search search = new Search();
        //System.out.println(search.getAllWebsites().toString());

        //return search.getAllWebsites();
    }
}
