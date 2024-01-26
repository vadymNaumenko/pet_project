package de.pet_project.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDTO {
    private Integer id;
    private String title;
    private byte[] picture;//path
    private String description;
    private boolean isDeleted;
}
