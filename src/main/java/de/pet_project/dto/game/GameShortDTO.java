package de.pet_project.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameShortDTO {
    private Integer id;
    private String image;
    private String title;
    private Double price;
    private String state;
    private String numberOfPlayers;
    private String minAge;
}
