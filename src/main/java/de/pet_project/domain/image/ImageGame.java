package de.pet_project.domain.image;

import de.pet_project.domain.game.Game;
import de.pet_project.domain.image.Image;
import de.pet_project.domain.enums.State;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "images_games")
public class ImageGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_game_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    private boolean isMain;
    private boolean isDeleted;
}
