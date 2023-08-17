package be.bnair.bevo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.services.NewsService;

import java.util.List;

@RestController
@RequestMapping(path = {"/news"})
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping(path = {"/list"})
    public List<NewsEntity> findAllAction() {
        return this.newsService.getAll();
    }
}