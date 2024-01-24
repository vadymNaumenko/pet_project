package de.pet_project.domain;

import de.pet_project.domain.enums.State;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "addresses")
public class Address {//TODO podvyazat game and promo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    private String country;

    @Column(name = "city")
    private String city;
    private String street;
    private String streetNumber;
    private Integer houseNumber;

    private boolean isDeleted;
}

