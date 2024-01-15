package de.pet_project.dto.promotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionShortDTO {
    private Integer id;
    private String image;
    private String title;
    private String state;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate startTime;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate endTime;
}
