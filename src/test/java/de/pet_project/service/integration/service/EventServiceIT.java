package de.pet_project.service.integration.service;

import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventDTO;
import de.pet_project.service.EventService;
import de.pet_project.service.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@IT
@RequiredArgsConstructor
public class EventServiceIT {

    private final EventService eventService;

    @Test
    void findById() {
        Optional<EventDTO> actual = eventService.findById(1L);
        assertTrue(actual.isPresent());
        assertEquals(1L,actual.get().getId());
    }
    @Test
    void findAllByPageable(){
        Sort.TypedSort<Event> sort = Sort.sort(Event.class);
        sort.by(Event::getDate);
        PageRequest pageable = PageRequest.of(0,8,sort.descending());
        Page<Event> actual = eventService.findAll(pageable);
        assertTrue(actual.hasContent());
        assertEquals(8,actual.getContent().size());
        assertTrue(actual.getContent().get(0).getDate().isAfter(actual.getContent().get(7).getDate()));
    }

}
