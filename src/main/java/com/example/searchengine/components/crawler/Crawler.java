package com.example.searchengine.components.crawler;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

@Component
public class Crawler {
    public Queue<String> urlQueue;
    public Crawler(){
        urlQueue= new LinkedList<>(asList("https://no.wikipedia.org/wiki/Yoga"));

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
        Pattern pattern = Pattern.compile("(https?|ftp)://(-\\.)?([^\\s/?\\.#]+\\.?)+(/[^\\s]*)?[^>]");
        Matcher contentToMatch = pattern.matcher(webpage);
        while(contentToMatch.find()){
            System.out.println(contentToMatch.group());
        }


    }

    public void crawl(){
        Crawler crawler = new Crawler();
        if(!crawler.urlQueue.isEmpty()){
            String requestUrl=crawler.urlQueue.remove();
            System.out.println("from crawler: "+requestUrl);
            crawler.readWebpage(requestUrl);


        }else{
            System.out.println("queue empty");
        }

    }

    public static void main(String[] args){
    }
}






