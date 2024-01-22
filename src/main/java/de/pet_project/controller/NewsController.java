package de.pet_project.controller;

import de.pet_project.domain.post.News;
import de.pet_project.dto.event.NewsDTO;
import de.pet_project.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class NewsController {

    private final NewsService eventService;
    @GetMapping()
    public Page<NewsDTO> getAll(Pageable pageable){
//        pageable.getSortOr()
        return eventService.findAll(pageable);
    }
    @GetMapping("{page}")
    public Page<NewsDTO> getAll(@PathVariable Integer page){
        Sort.TypedSort<News> sort = Sort.sort(News.class);
        sort.by(News::getDate);
        PageRequest pageable = PageRequest.of(page,16,sort.descending());
        return eventService.findAll(pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getById(@PathVariable Long id) {
      Optional<NewsDTO> eventDTO = eventService.findById(id);
        return eventDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    //todo add delete method

    @PutMapping
    public ResponseEntity<NewsDTO> update(@RequestBody NewsDTO eventDTO){
       return eventService.update(eventDTO)
               .map(ResponseEntity::ok)
               .orElseGet(()-> ResponseEntity.badRequest().build());
    }
}