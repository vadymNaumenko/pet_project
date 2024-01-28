package de.pet_project.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationGameDTO {
    private Integer id;
    private Integer gameId;
    private Integer addressId;
    private Boolean isDeleted;
}
