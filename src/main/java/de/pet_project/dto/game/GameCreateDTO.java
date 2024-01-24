package de.pet_project.dto.game;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class GameCreateDTO {
    private Integer id;
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
