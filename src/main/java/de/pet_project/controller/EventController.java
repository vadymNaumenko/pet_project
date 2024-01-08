package de.pet_project.controller;

import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventDTO;
import de.pet_project.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;
    @GetMapping()
    public Page<Event> getAll(Pageable pageable){
//        pageable.getSortOr()
        return eventService.findAll(pageable);
    }
    @GetMapping("{page}")
    public Page<Event> getAll( @PathVariable Integer page){
        Sort.TypedSort<Event> sort = Sort.sort(Event.class);
        sort.by(Event::getDate);
        PageRequest pageable = PageRequest.of(page,16,sort.descending());
        return eventService.findAll(pageable);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
      Optional< EventDTO> eventDTO = eventService.findById(id);
        return eventDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    //todo add delete method
}