package com.example.cantinjectrepo.service;

import com.example.cantinjectrepo.dto.NewsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    public ResponseEntity<String> saveData(NewsDTO newsPOJO) {
        return ResponseEntity.ok(newsPOJO.getTitle() + " [" + newsPOJO.getContent() + "]");
    }

}
