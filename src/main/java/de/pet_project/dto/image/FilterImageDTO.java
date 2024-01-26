package de.pet_project.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterImageDTO {
    private Integer entityId;
    private boolean isMain;
}
