package com.example.searchengine.controller;

import com.example.searchengine.components.crawler.Crawler;
import com.example.searchengine.components.search.Search;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SearchPage {
    @GetMapping("/")
    public String home(){
        //Crawler crawler = new Crawler();
        //crawler.crawl();
        Search search = new Search();
        System.out.println(search.getAllWebsites().toString());

        return "searchPage";
    }
}
