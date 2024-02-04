package de.pet_project.controller;

import de.pet_project.domain.news.News;
import de.pet_project.dto.news.NewsDTO;
import de.pet_project.dto.news.NewsShortCreateDTO;
import de.pet_project.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
@Slf4j
public class NewsController {

    private final NewsService newsService;

    @GetMapping()
    public Page<NewsDTO> getAll() {
        log.info("Fetching all news");
        Page<NewsDTO> news = newsService.findAll(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("date"))));
        return news;
    }

    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsShortCreateDTO createDTO) {
        log.info("Creating news");
        Optional<NewsDTO> newsDTO = newsService.createNews(createDTO);
        return newsDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getById(@PathVariable Long id) {
        log.info("Fetching news by id: {}", id);
        Optional<NewsDTO> newsDTO = newsService.findById(id);
        return newsDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping
    public ResponseEntity<NewsDTO> update(@RequestBody NewsDTO eventDTO) {
        log.info("Updating news with id: {}", eventDTO.getId());
        return newsService.update(eventDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
