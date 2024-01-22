package de.pet_project.dto.game;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class GameCreateUpdateDTO {
    private Integer id;
    private String title;
    private Double price;
    private String session;
    private String description;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate releaseDate;
}
