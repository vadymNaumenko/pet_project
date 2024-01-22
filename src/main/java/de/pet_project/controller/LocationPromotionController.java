package de.pet_project.controller;

import de.pet_project.domain.LocationPromotion;
import de.pet_project.dto.location.LocationPromotionDTO;
import de.pet_project.repository.LocationPromotionRepository;
import de.pet_project.service.LocationPromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/location_promotions")
public class LocationPromotionController {
    private final LocationPromotionService locationPromotionService;


    @PostMapping()
    public ResponseEntity<LocationPromotionDTO> save(@RequestBody LocationPromotionDTO locationPromotionDTO) {
        LocationPromotionDTO response = locationPromotionService.save(locationPromotionDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<LocationPromotionDTO> update(@RequestBody LocationPromotionDTO locationPromotionDTO) {
        LocationPromotionDTO response = locationPromotionService.save(locationPromotionDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{locationPromotionId}")
    public ResponseEntity<LocationPromotionDTO> deleteLocationPromotion(@PathVariable Integer locationPromotionId) {
        LocationPromotionDTO response = locationPromotionService.deleteLocationPromotion(locationPromotionId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
