package de.pet_project.service.integration.service;

import de.pet_project.domain.post.News;
import de.pet_project.dto.event.NewsDTO;
import de.pet_project.service.NewsService;
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

    private final NewsService eventService;

    @Test
    void findById() {
        Optional<NewsDTO> actual = eventService.findById(1L);
        assertTrue(actual.isPresent());
        assertEquals(1L, actual.get().getId());
    }

    @Test
    void findAllByPageable() {
        Sort.TypedSort<News> sort = Sort.sort(News.class);
        sort.by(News::getDate);
        PageRequest pageable = PageRequest.of(0, 8, sort.descending());
        Page<NewsDTO> actual = eventService.findAll(pageable);
        assertTrue(actual.hasContent());
        assertEquals(8, actual.getContent().size());
        assertTrue(actual.getContent().get(0).getDate().isAfter(actual.getContent().get(7).getDate()));
    }

    @Test
    void updateEvent() {
        Sort.TypedSort<News> sort = Sort.sort(News.class);
        sort.by(News::getDate);
        PageRequest pageable = PageRequest.of(0, 1, sort.descending());
        NewsDTO update = eventService.findAll(pageable).getContent().get(0);
        String newTitle = "CS 1.5";
        update.setTitle(newTitle);
        Optional<NewsDTO> actual = eventService.update(update);

        assertEquals(newTitle, actual.get().getTitle());
        assertTrue(eventService.findById(1234567L).isEmpty());

    }

    @Test
    void deleteById() {
        List<NewsDTO> eventDTO = eventService.findAllByTitle("Evil 4");
        Optional<NewsDTO> actual = eventService.delete(eventDTO.get(0).getId());
        assertTrue(actual.isPresent());
        assertTrue(actual.get().getIsDeleted());
    }

    @Test
    void findAllByTitle() {
        List<NewsDTO> eventDTO = eventService.findAllByTitle("Demeo B");
        assertFalse(eventDTO.isEmpty());
        assertEquals(2, eventDTO.size());
    }

    @Test
    void findByTitle() {
        List<NewsDTO> eventDTO = eventService.findByTitle("Demeo B");
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
