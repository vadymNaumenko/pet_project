package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    private String image;
    private String title;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String numberOfPlayers;
    private String minAge;
    private String description;
    private LocalDate releaseDate;
}
