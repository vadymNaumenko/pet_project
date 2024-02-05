package de.pet_project.controller;

import de.pet_project.dto.promotion.PromotionCreateDTO;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import de.pet_project.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/promotions")
@Slf4j
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping("/{addressId}")
    public Page<PromotionShortDTO> findAllByAddress(@PathVariable Integer addressId, @RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize) {
        log.info("Finding all promotions by address with ID: {}", addressId);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<PromotionShortDTO> result = promotionService.findAllByAddress(pageable, addressId);
        log.info("Found {} promotions by address with ID: {}", result.getTotalElements(), addressId);
        return result;
    }

    @GetMapping("/{city}")
    public Page<PromotionShortDTO> findAllByCity(@PathVariable String city, @RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize) {
        log.info("Finding all promotions by city: {}", city);
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<PromotionShortDTO> result = promotionService.findAllByCity(pageable, city);
        log.info("Found {} promotions by city: {}", result.getTotalElements(), city);
        return result;
    }

    @GetMapping()
    public Page<PromotionShortDTO> findAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        log.info("Finding all promotions");
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<PromotionShortDTO> result = promotionService.findAll(pageable);
        log.info("Found {} promotions", result.getTotalElements());
        return result;
    }

    @GetMapping("/{id}")
    public PromotionDTO findById(@PathVariable Integer id) {
        log.info("Finding promotion by ID: {}", id);
        PromotionDTO result = promotionService.findById(id);
        if (result != null) {
            log.info("Found promotion by ID: {}", id);
        } else {
            log.warn("Promotion not found by ID: {}", id);
        }
        return result;
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable Integer id) {
        log.info("Finding image for promotion with ID: {}", id);
        return promotionService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(() -> {
                    log.warn("Image not found for promotion with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping()
    public PromotionDTO save(@RequestBody PromotionCreateDTO promotionCreateDTO) {
        log.info("Saving new promotion: {}", promotionCreateDTO);
        PromotionDTO result = promotionService.save(promotionCreateDTO);
        log.info("New promotion saved successfully");
        return result;
    }

    @PutMapping()
    public ResponseEntity<PromotionDTO> update(@RequestBody PromotionDTO promotionDTO) {
        log.info("Updating promotion with ID: {}", promotionDTO.getId());
        PromotionDTO response = promotionService.update(promotionDTO);
        if (response == null) {
            log.warn("Failed to update the promotion");
            return ResponseEntity.notFound().build();
        }
        log.info("Promotion updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PromotionDTO> delete(@PathVariable Integer id) {
        log.info("Deleting promotion with ID: {}", id);
        PromotionDTO response = promotionService.delete(id);
        if (response == null) {
            log.warn("Failed to delete the promotion");
            return ResponseEntity.notFound().build();
        }
        log.info("Promotion deleted successfully");
        return ResponseEntity.ok(response);
    }
}
