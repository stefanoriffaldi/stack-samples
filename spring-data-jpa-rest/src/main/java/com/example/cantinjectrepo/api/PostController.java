package com.example.cantinjectrepo.api;

import com.example.cantinjectrepo.dto.NewsDTO;
import com.example.cantinjectrepo.service.NewsService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final NewsService newsService;

    @PostMapping("/addNews")
    public ResponseEntity<String> addNews(@RequestBody NewsDTO newsPOJO) {
        Instant start = Instant.now();
        log.info("AddNews Method");
        log.debug(newsPOJO.toString());
        log.debug("Duration: {}ms", Duration.between(Instant.now(), start).toMillis());
        return newsService.saveData(newsPOJO);
    }
}
