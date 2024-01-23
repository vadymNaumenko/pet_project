package de.pet_project.dto.image;

import lombok.Data;

@Data
public class ImageGameDTO {
    private Integer id;
    private Integer gameId;
    private Integer imageId;
    private String state;
}
