package de.pet_project.dto.promotion;

import lombok.Data;

@Data
public class FilterPromotionDTO {
    private Integer addressId;
    private String city;
    private String state;
}
