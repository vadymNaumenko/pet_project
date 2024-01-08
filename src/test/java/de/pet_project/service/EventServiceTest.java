package de.pet_project.service;

import de.pet_project.convertor.EventDtoConvertor;
import de.pet_project.domain.Event;
import de.pet_project.dto.event.EventDTO;
import de.pet_project.repository.EventRepository;
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
    private EventRepository eventRepository;
    @Mock
    private EventDtoConvertor eventDtoConvertor;
    @InjectMocks
    private EventService eventService;
    @Test
    void findById() {
        // Arrange
        Event mockEvent = new Event();
        Mockito.doReturn(Optional.of(mockEvent))
                .when(eventRepository).findById(36L);

        Optional<Event> actual = eventRepository.findById(36L);

        assertTrue(actual.isPresent());

        verify(eventRepository, times(1)).findById(36L); // Проверяем, что findById вызывается ровно один раз с правильным аргументом
        verifyNoMoreInteractions(eventRepository); // Проверяем, что больше нет взаимодействий с eventRepository
    }

}