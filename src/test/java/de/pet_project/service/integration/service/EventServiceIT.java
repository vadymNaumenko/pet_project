package de.pet_project.service.integration.service;

import de.pet_project.dto.event.EventDTO;
import de.pet_project.service.EventService;
import de.pet_project.service.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@IT
@RequiredArgsConstructor
public class EventServiceIT {

    private final EventService eventService;

    @Test
    void findById() {
        Optional<EventDTO> actual = eventService.findById(36L);
        assertTrue(actual.isPresent());

    }
}
