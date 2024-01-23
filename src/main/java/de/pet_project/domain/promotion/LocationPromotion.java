package de.pet_project.domain.promotion;

import de.pet_project.domain.Address;
import de.pet_project.domain.enums.State;
import de.pet_project.domain.promotion.Promotion;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "location_promotions")
public class LocationPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_promotion_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    private State state;
}
