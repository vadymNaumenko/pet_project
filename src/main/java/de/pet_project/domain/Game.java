package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    private String image;
    private String title;
    private String description;
    private String genres;

    @ManyToOne
    @JoinColumn(name = "map_id")
    private Map map;

    private LocalDate releaseDate;
}
