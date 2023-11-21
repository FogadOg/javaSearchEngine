package com.example.searchengine.components.search.website;

import com.example.searchengine.components.search.website.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebsiteController {

    private final WebsiteService websiteService;

    @Autowired
    public WebsiteController(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    @GetMapping("/search")
    public List<Website> getAllWebsites() {
        return websiteService.getAllWebsites();
    }
}
