package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    private LocalDate startTime;
    private LocalDate endTime;
    private Double price;
}
