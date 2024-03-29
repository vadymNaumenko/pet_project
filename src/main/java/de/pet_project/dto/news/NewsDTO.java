package de.pet_project.dto.news;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {
    private Long id ;
    private String title;
    private String imageUrl;
    private String text;
    private Boolean isDeleted;
    private int sizeComments;
    private LocalDate date;
}
