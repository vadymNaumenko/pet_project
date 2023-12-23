package de.pet_project.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "addresses")
public class Address {
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
}
