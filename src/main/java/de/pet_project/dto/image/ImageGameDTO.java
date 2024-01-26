package de.pet_project.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageGameDTO {
    private Integer id;
    private Integer gameId;
    private Integer imageId;
    private boolean isMain;
    private boolean isDeleted;
}
