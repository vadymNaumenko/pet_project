package de.pet_project.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterPromotionDTO {
    private Integer addressId;
    private String city;
    private String state;
}
