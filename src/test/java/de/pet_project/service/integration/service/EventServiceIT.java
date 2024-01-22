package de.pet_project.service.integration.service;

import de.pet_project.domain.post.Event;
import de.pet_project.dto.event.EventDTO;
import de.pet_project.service.EventService;
import de.pet_project.service.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@IT
@RequiredArgsConstructor
//@WithMockUser(username = "ivan@gmail.com", password = "1Fishka1!", authorities = "ADMIN")
public class EventServiceIT {

    private final EventService eventService;

    @Test
    void findById() {
        Optional<EventDTO> actual = eventService.findById(1L);
        assertTrue(actual.isPresent());
        assertEquals(1L, actual.get().getId());
    }

    @Test
    void findAllByPageable() {
        Sort.TypedSort<Event> sort = Sort.sort(Event.class);
        sort.by(Event::getDate);
        PageRequest pageable = PageRequest.of(0, 8, sort.descending());
        Page<EventDTO> actual = eventService.findAll(pageable);
        assertTrue(actual.hasContent());
        assertEquals(8, actual.getContent().size());
        assertTrue(actual.getContent().get(0).getDate().isAfter(actual.getContent().get(7).getDate()));
    }

    @Test
    void updateEvent() {
        Sort.TypedSort<Event> sort = Sort.sort(Event.class);
        sort.by(Event::getDate);
        PageRequest pageable = PageRequest.of(0, 1, sort.descending());
        EventDTO update = eventService.findAll(pageable).getContent().get(0);
        String newTitle = "CS 1.5";
        update.setTitle(newTitle);
        Optional<EventDTO> actual = eventService.update(update);

        assertEquals(newTitle, actual.get().getTitle());
        assertTrue(eventService.findById(1234567L).isEmpty());

    }

    @Test
    void deleteById() {
        List<EventDTO> eventDTO = eventService.findAllByTitle("Evil 4");
        Optional<EventDTO> actual = eventService.delete(eventDTO.get(0).getId());
        assertTrue(actual.isPresent());
        assertTrue(actual.get().getIsDeleted());
    }

    @Test
    void findAllByTitle() {
        List<EventDTO> eventDTO = eventService.findAllByTitle("Demeo B");
        assertFalse(eventDTO.isEmpty());
        assertEquals(2, eventDTO.size());
    }

    @Test
    void findByTitle() {
        List<EventDTO> eventDTO = eventService.findByTitle("Demeo B");
        assertFalse(eventDTO.isEmpty());
        assertEquals(1, eventDTO.size());
        assertFalse(eventDTO.get(0).getIsDeleted());
    }

    @Test
    void hasEvent() {
        assertTrue(eventService.hasEvent(
                "https://www.uploadvr.com/content/images/size/w800/format/webp/2023/12/KeyArt_16_9_Without-Logo-1.png"));
    }

}
