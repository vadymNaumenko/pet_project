package de.pet_project.dto.news;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsCreateDTO {
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String text;
    private String dateTime;

}
