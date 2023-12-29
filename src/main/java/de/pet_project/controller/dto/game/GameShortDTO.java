package de.pet_project.controller.dto.game;

import de.pet_project.domain.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameShortDTO {
    private Integer id;
    private MultipartFile image;
    private String title;
    private Double price;
    //private String state;
    private String numberOfPlayers;
    private String minAge;


    public GameShortDTO(Integer id, String title, Double price, String numberOfPlayers,
                        String minAge) {
        this.id = id;
        this.title = title;
        this.price = price;
        //this.state = state;
        this.numberOfPlayers = numberOfPlayers;
        this.minAge = minAge;
    }

    public static GameShortDTO getInstance(Game game) {
        return new GameShortDTO(game.getId(), game.getTitle(), game.getPrice(),
                game.getNumberOfPlayers().number, game.getMinAge().age);
    }
}
