package de.pet_project.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterGameDTO {
    private Integer addressId;
    private String city;
    private String genre;
    private String state;
    private String numberOfPlayers;
    private String minAge;
}
