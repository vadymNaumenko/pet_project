package de.pet_project.controller;

import de.pet_project.domain.news.News;
import de.pet_project.dto.news.NewsCreateDTO;
import de.pet_project.dto.news.NewsDTO;
import de.pet_project.dto.news.NewsShortCreateDTO;
import de.pet_project.service.CommentOnNewsService;
import de.pet_project.service.NewsService;
import de.pet_project.service.ReactionToNewsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping()
    public Page<NewsDTO> getAll(Pageable pageable){
//        pageable.getSortOr()
        Page<NewsDTO> news = newsService.findAll(pageable);
        return news;
    }
    @GetMapping("/{pageNumber}/{pageSize}")
    public Page<NewsDTO> getAll(@PathVariable Integer pageNumber,@PathVariable Integer pageSize){
        Sort.TypedSort<News> sort = Sort.sort(News.class);
        sort.by(News::getDate);
        PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort.descending());
        return newsService.findAll(pageable);
    }
    @PostMapping
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsShortCreateDTO createDTO){
        Optional<NewsDTO> newsDTO = newsService.createNews(createDTO);
        return newsDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getById(@PathVariable Long id) {
      Optional<NewsDTO> eventDTO = newsService.findById(id);
        return eventDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    //todo add delete method

    @PutMapping
    public ResponseEntity<NewsDTO> update(@RequestBody NewsDTO eventDTO){
       return newsService.update(eventDTO)
               .map(ResponseEntity::ok)
               .orElseGet(()-> ResponseEntity.badRequest().build());
    }
}