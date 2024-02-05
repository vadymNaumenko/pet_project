package de.pet_project.controller;

import de.pet_project.domain.LocationPromotion;
import de.pet_project.dto.location.LocationPromotionDTO;
import de.pet_project.repository.LocationPromotionRepository;
import de.pet_project.service.LocationPromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/location_promotions")
@Slf4j
public class LocationPromotionController {
    private final LocationPromotionService locationPromotionService;

    @PostMapping()
    public ResponseEntity<LocationPromotionDTO> save(@RequestBody LocationPromotionDTO locationPromotionDTO) {
        log.info("Saving new location promotion: {}", locationPromotionDTO);
        LocationPromotionDTO response = locationPromotionService.save(locationPromotionDTO);
        if (response == null) {
            log.warn("Failed to save location promotion");
            return ResponseEntity.notFound().build();
        }
        log.info("Location promotion saved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<LocationPromotionDTO> update(@RequestBody LocationPromotionDTO locationPromotionDTO) {
        log.info("Updating location promotion with ID: {}", locationPromotionDTO.getId());
        LocationPromotionDTO response = locationPromotionService.save(locationPromotionDTO);
        if (response == null) {
            log.warn("Failed to update the location promotion");
            return ResponseEntity.notFound().build();
        }
        log.info("Location promotion updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{locationPromotionId}")
    public ResponseEntity<LocationPromotionDTO> deleteLocationPromotion(@PathVariable Integer locationPromotionId) {
        log.info("Deleting location promotion with ID: {}", locationPromotionId);
        LocationPromotionDTO response = locationPromotionService.deleteLocationPromotion(locationPromotionId);
        if (response == null) {
            log.warn("Failed to delete the location promotion");
            return ResponseEntity.notFound().build();
        }
        log.info("Location promotion deleted successfully");
        return ResponseEntity.ok(response);
    }
}
