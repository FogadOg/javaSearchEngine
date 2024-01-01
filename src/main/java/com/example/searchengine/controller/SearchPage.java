package com.example.searchengine.controller;

import com.example.searchengine.components.crawler.Crawler;
import com.example.searchengine.components.search.WebsiteSearch;
import org.json.JSONArray;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchPage {

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/crawl")
    public void home(){
        Crawler crawler=new Crawler();
        crawler.crawl();

    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<String> search(@PathVariable String searchTerm) {
        String message=String.format("POST to path 'search/%1$s'",searchTerm);
        System.out.println(message);
        WebsiteSearch websiteSearch = new WebsiteSearch();

        JSONArray array= websiteSearch.getAllWebsites(searchTerm);

        return ResponseEntity.ok(array.toString());
    }

}
