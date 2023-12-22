package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private String session;
    private String numberOfPlayers;
    private String minAge;
    private String description;
    private LocalDate releaseDate;
}