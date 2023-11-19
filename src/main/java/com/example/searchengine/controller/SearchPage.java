package com.example.searchengine.controller;

import com.example.searchengine.components.crawler.Crawler;
import com.example.searchengine.components.search.Search;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchPage {
    @GetMapping("/")
    public String home(){
        //Crawler crawler = new Crawler();
        //crawler.crawl();
        Search searchResults= new Search("test search term");
        System.out.println(searchResults.getSearchResults());
        return "searchPage";
    }
}
