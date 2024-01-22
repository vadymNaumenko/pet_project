package de.pet_project.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameDTO {
    private Integer id;
    private String image;//TODO????
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