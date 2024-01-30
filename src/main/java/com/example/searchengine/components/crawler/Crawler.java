package com.example.searchengine.components.crawler;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

@Component
public class Crawler {
    public Queue<String> urlQueue;
    public List<String> urlsCrawled;

    CrawlerService crawlerService;



    public Crawler(){
        this.urlQueue= new LinkedList<>(asList(
                "https://www.ibm.com/topics/machine-learning",
                "https://www.geeksforgeeks.org/data-structures/",
                "https://www.ibm.com/topics/natural-language-processing",
                "https://pytorch.org",
                "https://www.nvidia.com/en-us/glossary/pytorch/"

        ));
        this.urlsCrawled= new ArrayList<>();
        this.crawlerService=new CrawlerService();

    }

    public void crawl(){
        Crawler crawler= new Crawler();

        while(!this.urlQueue.isEmpty()){
            String requestUrl=this.urlQueue.remove();
            readWebpage(requestUrl);

        }
        System.out.println("queue empty");


    }

    private void readWebpage(String requestUrl){
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(requestUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            findUrlsInHtml(response.body());


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void findUrlsInHtml(String webpage){

        String regexPattern = "<a\\s+[^>]*href\\s*=\\s*\"([^\"]*)\"[^>]*>|<a\\s+[^>]*href\\s*=\\s*'([^']*)'[^>]*>";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher foundUrls = pattern.matcher(webpage);
        findUrls(foundUrls);

    }

    private void findUrls(@NonNull Matcher foundUrls){
        while (foundUrls.find()) {
            String url = foundUrls.group(1) != null ? foundUrls.group(1) : foundUrls.group(2);
            this.crawlerService.incrementBackLinkCount(url);
            boolean isUrlInJsonFile = this.crawlerService.checkIfPageInJsonFile(url, "data.json");

            if (!isUrlInJsonFile) {
                if (url.startsWith("http")) {
                    this.crawlerService.addPageToJson(url);
                    this.urlQueue.add(url);

                }

            }
        }

    }

}






