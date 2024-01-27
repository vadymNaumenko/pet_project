package de.pet_project.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationPromotionDTO {
    private Integer id;
    private Integer promotionId;
    private Integer addressId;
    private Boolean isDeleted;
}
