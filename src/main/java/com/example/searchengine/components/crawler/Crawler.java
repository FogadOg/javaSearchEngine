package com.example.searchengine.components.crawler;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
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
            this.readWebpage(requestUrl);


        }
        System.out.println("queue empty");


    }

    private String readWebpage(String requestUrl){
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
        }finally {
            return "web page";
        }
    }

    public void findUrlsInHtml(String webpage){

        Crawler crawler= new Crawler();

        Integer breakingPoint=crawler.breakingPoint(2);

        //"<a\\s+[^>]*href\\s*=\\s*\"(https?://[^\\s/\"]+)\"[^>]*>|<a\\s+[^>]*href\\s*=\\s*'((https?://[^\\s/\']+))'[^>]*>"

        String regexPattern = "<a\\s+[^>]*href\\s*=\\s*\"([^\"]*)\"[^>]*>|<a\\s+[^>]*href\\s*=\\s*'([^']*)'[^>]*>";

        Pattern pattern = Pattern.compile(regexPattern);

        Matcher matcher = pattern.matcher(webpage);

        while (matcher.find()) {
            String url = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);

            if(!this.urlsCrawled.contains(url)){

                if(url.startsWith("http")){
                    this.urlsCrawled.add(url);
                    //System.out.println("Found URL: " + url);
                    this.urlQueue.add(url);
                }

            }

        }


    }

    private boolean checkIfStringStartsWithHttp(String url){
        if(url.startsWith("http")){
            return true;
        }else{
            return false;
        }
    }

    private Integer breakingPoint(Integer breakingPoint){
        return breakingPoint-1;
    }

    public static void main(String[] args){
    }
}






