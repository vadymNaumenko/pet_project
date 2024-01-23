package de.pet_project.service;

import de.pet_project.convertor.NewsDtoConvertor;
import de.pet_project.domain.news.News;
import de.pet_project.repository.news_and_comment.NewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @Mock
    private NewsRepository eventRepository;
    @Mock
    private NewsDtoConvertor eventDtoConvertor;
    @InjectMocks
    private NewsService eventService;
    @Test
    void findById() {
        // Arrange
        News mockEvent = new News();
        Mockito.doReturn(Optional.of(mockEvent))
                .when(eventRepository).findById(36L);

        Optional<News> actual = eventRepository.findById(36L);

        assertTrue(actual.isPresent());

        verify(eventRepository, times(1)).findById(36L); // Проверяем, что findById вызывается ровно один раз с правильным аргументом
        verifyNoMoreInteractions(eventRepository); // Проверяем, что больше нет взаимодействий с eventRepository
    }

}