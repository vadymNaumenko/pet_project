package de.pet_project.dto.image;

import lombok.Data;

@Data
public class ImageDTO {
    private Integer id;
    private String title;
    private byte[] picture;
    private String description;
    private boolean isDeleted;
}
