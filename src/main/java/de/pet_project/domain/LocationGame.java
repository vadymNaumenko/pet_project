package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "location_games")
public class LocationGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_game_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private Boolean isDeleted;
}
