package de.pet_project.controller.promotion;

import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import de.pet_project.service.promotion.PromotionService;
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

    @PostMapping()
    public PromotionDTO save(@RequestBody PromotionDTO promotionDTO) {
        return promotionService.save(promotionDTO);
    }

    @PutMapping()
    public ResponseEntity<PromotionDTO> update(@RequestBody PromotionDTO promotionDTO) {
        PromotionDTO response = promotionService.update(promotionDTO);
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
