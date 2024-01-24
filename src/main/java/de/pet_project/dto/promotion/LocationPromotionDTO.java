package de.pet_project.dto.promotion;

import lombok.Data;

@Data
public class LocationPromotionDTO {
    private Integer id;
    private Integer promotionId;
    private Integer addressId;
    private boolean isDeleted;
}
