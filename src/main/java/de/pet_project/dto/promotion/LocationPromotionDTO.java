package de.pet_project.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationPromotionDTO {
    private Integer id;
    private Integer promotionId;
    private Integer addressId;
    private boolean isDeleted;
}
