package de.pet_project.controller.dto.game;

import de.pet_project.domain.Game;
import de.pet_project.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class GameDTO {
    private String image;
    private String title;
    private Double price;
    private Genre genre;
    private String numberOfPlayers;
    private String minAge;
    private String description;
    private LocalDate releaseDate;

    public static GameDTO getInstance(Game game) {
        return new GameDTO(
                game.getImage(), game.getTitle(),
                game.getPrice(), game.getGenre(),
                game.getNumberOfPlayers(), game.getMinAge(),
                game.getDescription(), game.getReleaseDate()
        );
    }
}
