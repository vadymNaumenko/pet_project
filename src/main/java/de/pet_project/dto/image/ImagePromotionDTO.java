package de.pet_project.dto.image;

import lombok.Data;

@Data
public class ImagePromotionDTO {
    private Integer id;
    private Integer promotionId;
    private Integer imageId;
    private boolean isMain;
    private boolean isDeleted;
}
