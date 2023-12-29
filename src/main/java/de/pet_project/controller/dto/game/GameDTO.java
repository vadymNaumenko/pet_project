package de.pet_project.controller.dto.game;

import de.pet_project.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameDTO {
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

    public GameDTO(Integer id, String title, Double price, String genre,
                   String session, String numberOfPlayers, String minAge, String description,
                   LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.genre = genre;
        this.session = session;
        this.numberOfPlayers = numberOfPlayers;
        this.minAge = minAge;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public static GameDTO getInstance(Game game) {
        return new GameDTO(game.getId(), game.getTitle(), game.getPrice(), game.getGenre().genre,
                game.getSession(), game.getNumberOfPlayers().number,
                game.getMinAge().age, game.getDescription(), game.getReleaseDate());
    }
}