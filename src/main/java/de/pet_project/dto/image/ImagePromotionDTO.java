package de.pet_project.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImagePromotionDTO {
    private Integer id;
    private Integer promotionId;
    private Integer imageId;
    private boolean isMain;
    private boolean isDeleted;
}
