package de.pet_project.dto.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    private Long id ;
    private String title;
    private String imageUrl;
    private String text;
    private LocalDateTime dateTime;
}
