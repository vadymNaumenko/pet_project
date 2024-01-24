package de.pet_project.controller.promotion;

import de.pet_project.dto.promotion.FilterPromotionDTO;
import de.pet_project.dto.promotion.PromotionCreateDTO;
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

    @GetMapping("/filter")
    public Page<PromotionShortDTO> findAllByAddress(@RequestBody FilterPromotionDTO filterPromotionDTO, @RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return promotionService.findAllByFilter(filterPromotionDTO, pageable);
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
    public PromotionCreateDTO save(@RequestBody PromotionCreateDTO promotionCreateDTO) {
        return promotionService.save(promotionCreateDTO);
    }

    @PutMapping()
    public ResponseEntity<PromotionCreateDTO> update(@RequestBody PromotionCreateDTO promotionCreateDTO) {
        PromotionCreateDTO response = promotionService.update(promotionCreateDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PromotionCreateDTO> delete(@PathVariable Integer id) {
        PromotionCreateDTO response = promotionService.delete(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
