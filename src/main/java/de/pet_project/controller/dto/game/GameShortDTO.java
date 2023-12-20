package de.pet_project.controller.dto.game;

import de.pet_project.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GameShortDTO {
    private String image;
    private String title;
    private Double price;

    public static GameShortDTO getInstance(Game game) {
        return new GameShortDTO(game.getImage(), game.getTitle(), game.getPrice());
    }
}
