package com.example.searchengine.components.crawler;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
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
        this.urlQueue= new LinkedList<>(asList("https://no.wikipedia.org/wiki/Yoga"));
        this.urlsCrawled= new ArrayList<>();
        this.crawlerService=new CrawlerService();

    }


    public void crawl(){
        Crawler crawler= new Crawler();

        while(!this.urlQueue.isEmpty()){
            if (this.urlQueue.size()==2000) {
                break;
            }
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
        Crawler crawler= new Crawler();

        //"<a\\s+[^>]*href\\s*=\\s*\"(https?://[^\\s/\"]+)\"[^>]*>|<a\\s+[^>]*href\\s*=\\s*'((https?://[^\\s/\']+))'[^>]*>"

        String regexPattern = "<a\\s+[^>]*href\\s*=\\s*\"([^\"]*)\"[^>]*>|<a\\s+[^>]*href\\s*=\\s*'([^']*)'[^>]*>";

        Pattern pattern = Pattern.compile(regexPattern);

        Matcher foundUrls = pattern.matcher(webpage);

        while (foundUrls.find()) {
            String url = foundUrls.group(1) != null ? foundUrls.group(1) : foundUrls.group(2);
            boolean isUrlInJsonFile = this.crawlerService.checkIfPageInJsonFile(url, "data.json");

            if (!isUrlInJsonFile) {
                if (url.startsWith("http")) {
                    this.crawlerService.addUrlDataToJsonFile("data.json", url, LocalDateTime.now(), "cats", 5);


                    System.out.println("Found URL: " + url);
                    this.urlQueue.add(url);
                }

            }
        }

    }

    private void addUrlFromPageToJosnFile(Matcher foundUrls){




    }

    private Integer breakingPoint(Integer breakingPoint){
        return breakingPoint-1;
    }

    public static void main(String[] args){
    }
}






