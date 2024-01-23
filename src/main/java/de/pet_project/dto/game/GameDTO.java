package de.pet_project.dto.game;

import de.pet_project.dto.image.ImageDTO;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.util.List;

@Data
public class GameDTO {
    private Integer id;
    private List<ImageDTO> images;
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