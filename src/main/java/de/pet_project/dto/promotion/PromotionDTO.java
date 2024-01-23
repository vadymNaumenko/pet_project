package de.pet_project.dto.promotion;

import de.pet_project.dto.image.ImageDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
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
