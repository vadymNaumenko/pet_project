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
    private String text;
    private String urlToNews;
    private String dateTime;

    public EventCreateDTO(String title, String imageUrl, String urlToNews, String dateTime) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.urlToNews = urlToNews;
        this.dateTime = dateTime;
    }
}
