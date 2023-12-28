package de.pet_project.dto.game;

import de.pet_project.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GameDTO {
    private Integer id;
    private String image;
    private String title;
    private Double price;
    private String genre;
    private String session;
    private String numberOfPlayers;
    private String minAge;
    private String description;
    private LocalDate releaseDate;

    public static GameDTO getInstance(Game game) {
        return new GameDTO(game.getId(), game.getImage(), game.getTitle(), game.getPrice(), game.getGenre().name(),
                game.getSession(), game.getNumberOfPlayers(), game.getMinAge(), game.getDescription(),
                game.getReleaseDate());
    }
}