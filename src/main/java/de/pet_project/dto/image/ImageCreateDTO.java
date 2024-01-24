package de.pet_project.dto.image;

import lombok.Builder;
import lombok.Data;

@Data
public class ImageCreateDTO {
    private Integer id;
    private String title;
    private String description;
    private boolean isDeleted;
}
