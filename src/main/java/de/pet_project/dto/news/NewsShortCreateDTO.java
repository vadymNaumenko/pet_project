package de.pet_project.dto.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsShortCreateDTO {
    private String title;
    private String imageUrl;
    private String videoUrl;
    private String text;
}
