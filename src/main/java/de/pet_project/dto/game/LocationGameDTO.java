package de.pet_project.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationGameDTO {
    private Integer id;
    private Integer gameId;
    private Integer addressId;
    private boolean isDeleted;
}
