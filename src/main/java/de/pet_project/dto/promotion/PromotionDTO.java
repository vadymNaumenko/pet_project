package de.pet_project.dto.promotion;

import de.pet_project.dto.image.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionDTO {
    private Integer id;
    private List<ImageDTO> images;
    private String title;
    private String category;
    private String state;
    private String description;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate startTime;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate endTime;
}
