package de.pet_project.controller;

import de.pet_project.domain.LocationGame;
import de.pet_project.domain.LocationPromotion;
import de.pet_project.dto.location.LocationPromotionDTO;
import de.pet_project.dto.promotion.PromotionCreateUpdateDTO;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import de.pet_project.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping("all/address{addressId}/page/{pageNum}/{pageSize}")
    public Page<PromotionShortDTO> findAllByAddress(@PathVariable Integer addressId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return promotionService.findAllByAddress(pageable, addressId);
    }

    @GetMapping("all/city{city}/page/{pageNum}/{pageSize}")
    public Page<PromotionShortDTO> findAllByCity(@PathVariable String city, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return promotionService.findAllByCity(pageable, city);
    }


    //TODO get all categories
    //TODO find by categories
    @GetMapping("/all/page/{pageNum}/{pageSize}")
    public Page<PromotionShortDTO> findAll(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return promotionService.findAll(pageable);
    }

    @GetMapping("/promotions{id}")
    public PromotionDTO findById(@PathVariable Integer id) {
        return promotionService.findById(id);
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable Integer id) {
        return promotionService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping()
    public PromotionCreateUpdateDTO save(@RequestBody PromotionCreateUpdateDTO promotionCreateUpdateDTO) {
        return promotionService.save(promotionCreateUpdateDTO);
    }

    @PostMapping("location_promotions")
    public LocationPromotion save(@RequestBody LocationPromotionDTO locationPromotionDTO) {
        return promotionService.save(locationPromotionDTO);
    }

    @PutMapping()
    public ResponseEntity<PromotionCreateUpdateDTO> update(@RequestBody PromotionCreateUpdateDTO promotionCreateUpdateDTO) {
        PromotionCreateUpdateDTO response = promotionService.update(promotionCreateUpdateDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("location_games")
    public ResponseEntity<LocationPromotion> update(@RequestBody LocationPromotionDTO locationPromotionDTO) {
        LocationPromotion response = promotionService.update(locationPromotionDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PromotionDTO> delete(@PathVariable Integer id) {
        PromotionDTO response = promotionService.delete(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("location_promotions{locationPromotionId}")
    public ResponseEntity<LocationPromotion> deleteLocationPromotion(@PathVariable Integer locationPromotionId) {
        LocationPromotion response = promotionService.deleteLocationPromotion(locationPromotionId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
