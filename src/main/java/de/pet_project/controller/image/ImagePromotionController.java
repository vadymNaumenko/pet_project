package de.pet_project.controller.image;

import de.pet_project.dto.image.ImagePromotionDTO;
import de.pet_project.service.image.ImagePromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images_promotions")
public class ImagePromotionController {
    private final ImagePromotionService imagePromotionService;
    @PostMapping()
    public ResponseEntity<ImagePromotionDTO> save(@RequestBody ImagePromotionDTO imagePromotionDTO) {
        ImagePromotionDTO response = imagePromotionService.save(imagePromotionDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ImagePromotionDTO> update(@RequestBody ImagePromotionDTO imagePromotionDTO) {
        ImagePromotionDTO response = imagePromotionService.update(imagePromotionDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{imagePromotionId}")
    public ResponseEntity<ImagePromotionDTO> delete(@PathVariable Integer imagePromotionId) {
        ImagePromotionDTO response = imagePromotionService.delete(imagePromotionId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
