package com.example.searchengine.components.search.website;

import com.example.searchengine.components.search.website.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebsiteService {

    @Autowired
    private WebsiteRepository websiteRepository;


    public List<Website> getAllWebsites(){
        return websiteRepository.findAll();
    }
}
