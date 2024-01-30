package com.example.searchengine.controller;

import com.example.searchengine.components.crawler.Crawler;
import com.example.searchengine.components.search.ImageSearch;
import com.example.searchengine.components.search.WebsiteSearch;
import org.json.JSONArray;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchController {

    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/crawl")
    public void home(){
        Crawler crawler=new Crawler();
        crawler.crawl();

    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<String> searchWebsites(@PathVariable String searchTerm) {
        String message=String.format("GET to path 'search/%1$s'",searchTerm);
        System.out.println(message);
        WebsiteSearch websiteSearch = new WebsiteSearch();

        JSONArray websites = websiteSearch.getAllWebsites(searchTerm);

        return ResponseEntity.ok(websites.toString());
    }
    @CrossOrigin(origins = "http://127.0.0.1:3000")
    @GetMapping("/images/{searchTerm}")
    public ResponseEntity<String> searchImages(@PathVariable String searchTerm) {
        String message=String.format("GET to path 'images/%1$s'",searchTerm);
        System.out.println(message);
        WebsiteSearch websiteSearch = new WebsiteSearch();
        JSONArray websites= websiteSearch.getAllWebsites(searchTerm);

        ImageSearch imageSearch = new ImageSearch(websites, searchTerm);
        JSONArray relevantImages = imageSearch.search();

        return ResponseEntity.ok(relevantImages.toString());
    }

}
