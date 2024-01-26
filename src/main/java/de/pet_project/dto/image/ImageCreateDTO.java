package de.pet_project.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageCreateDTO {
    private Integer id;
    private String title;
    private String description;
    private boolean isDeleted;
}
