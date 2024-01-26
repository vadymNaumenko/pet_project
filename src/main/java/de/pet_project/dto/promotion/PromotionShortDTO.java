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
public class PromotionShortDTO {
    private Integer id;
    private ImageDTO image;
    private String title;
    private String state;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate startTime;


    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate endTime;
}
