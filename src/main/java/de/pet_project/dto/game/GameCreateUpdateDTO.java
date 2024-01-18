package de.pet_project.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameCreateUpdateDTO {
    private Integer id;
    private MultipartFile image;
    private String title;
    private Double price;
    private String genre;
    private String state;
    private String session;
    private String numberOfPlayers;
    private String minAge;
    private String description;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate releaseDate;
}
