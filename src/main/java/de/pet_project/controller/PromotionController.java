package de.pet_project.controller;

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
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    @GetMapping("/{addressId}")
    public Page<PromotionShortDTO> findAllByAddress(@PathVariable Integer addressId, @RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return promotionService.findAllByAddress(pageable, addressId);
    }

    @GetMapping("/{city}")
    public Page<PromotionShortDTO> findAllByCity(@PathVariable String city, @RequestParam("pageNum") Integer pageNum,
                                                 @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return promotionService.findAllByCity(pageable, city);
    }

    @GetMapping()
    public Page<PromotionShortDTO> findAll(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return promotionService.findAll(pageable);
    }

    @GetMapping("/{id}")
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

    @PutMapping()
    public ResponseEntity<PromotionCreateUpdateDTO> update(@RequestBody PromotionCreateUpdateDTO promotionCreateUpdateDTO) {
        PromotionCreateUpdateDTO response = promotionService.update(promotionCreateUpdateDTO);
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
}
