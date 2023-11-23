package com.example.searchengine.components.search.website;


public class Website {
    private String url;
    private String pageTitle;
    private String favicon;
    private Long rating;
    private String content;
    private String lastTimeCrawled;

    public Website(String url, String pageTitle, String favicon, Long rating, String content, String lastTimeCrawled){
        this.url=url;
        this.pageTitle=pageTitle;
        this.favicon=favicon;
        this.rating=rating;
        this.content=content;
        this.lastTimeCrawled=lastTimeCrawled;
    }




}


