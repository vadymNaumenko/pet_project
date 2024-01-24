package de.pet_project.dto.game;

import de.pet_project.dto.image.ImageDTO;
import lombok.Data;


@Data
public class GameShortDTO {
    private Integer id;
    private ImageDTO image;
    private String title;
    private Double price;
    private String state;
    private String numberOfPlayers;
    private String minAge;
}
