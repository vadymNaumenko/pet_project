package de.pet_project.dto.game;

import lombok.Data;

@Data
public class GameV2DTO {
    private String address;
    private String city;
    private String genre;
    private String state;
    private String numberOfPlayers;
    private String minAge;
}
