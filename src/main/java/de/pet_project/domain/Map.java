package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "maps")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    private Integer id;

    private String image;
    private String title;
    private String description;
    private String genres;
    private String complexity;
    private LocalDate releaseDate;
}
