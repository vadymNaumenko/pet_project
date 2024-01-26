package de.pet_project.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
