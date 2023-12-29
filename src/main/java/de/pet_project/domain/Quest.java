package de.pet_project.domain;

import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "quests")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Integer id;

    private String image;
    private String title;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private State state;

    private String session;

    @Enumerated(EnumType.STRING)
    private NumberOfPlayers numberOfPlayers;

    @Enumerated(EnumType.STRING)
    private MinAge minAge;

    private String description;
    private LocalDate releaseDate;
}
