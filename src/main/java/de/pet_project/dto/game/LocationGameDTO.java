package de.pet_project.dto.game;

import lombok.Data;

@Data
public class LocationGameDTO {
    private Integer id;
    private Integer gameId;
    private Integer addressId;
    private boolean isDeleted;
}
