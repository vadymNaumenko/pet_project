package de.pet_project.dto.promotion;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PromotionCreateDTO {
    private Integer id;
    private String title;
    private String category;
    private String state;
    private String description;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate startTime;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate endTime;
}
