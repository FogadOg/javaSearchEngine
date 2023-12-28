package com.example.searchengine.controller;

import com.example.searchengine.components.crawler.Crawler;
import com.example.searchengine.components.search.ImageSearch;
import com.example.searchengine.components.search.WebsiteSearch;
import org.json.simple.JSONArray;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchPage {

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/crawl")
    public void home(){
        Crawler crwaler=new Crawler();
        crwaler.crawl();

    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/search/{searchTerm}")
    public JSONArray search(@PathVariable String searchTerm){
        String message=String.format("POST to path 'search/%1$s'",searchTerm);
        System.out.println(message);
        WebsiteSearch websiteSearch = new WebsiteSearch();

        return websiteSearch.getAllWebsites(searchTerm);
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/images/{searchTerm}")
    public JSONArray searchImages(@PathVariable String searchTerm){
        ImageSearch imageSearch = new ImageSearch();

        return imageSearch.getAllImages(searchTerm);
    }
}
