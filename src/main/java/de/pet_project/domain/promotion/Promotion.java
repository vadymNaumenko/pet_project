package de.pet_project.domain.promotion;

import de.pet_project.domain.enums.State;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Integer id;
    //TODO telo promo

    private String title;
    private String category;

    @Enumerated(EnumType.STRING)
    private State state;

    private String description;
    private LocalDate startTime;
    private LocalDate endTime;
}