package de.pet_project.dto.event;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventCreateDTO {
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String text;
    private String dateTime;

}
