package de.pet_project.service.promotion;

import de.pet_project.convertor.PromotionDtoConvert;
import de.pet_project.domain.promotion.Promotion;
import de.pet_project.domain.enums.State;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import de.pet_project.repository.promotion.LocationPromotionRepository;
import de.pet_project.repository.promotion.PromotionRepository;
import de.pet_project.service.image.ImageService;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionDtoConvert promotionDtoConvert;
    private final ImageService imageService;
    private final LocationPromotionRepository locationPromotionRepository;

    public Page<PromotionShortDTO> findAllByAddress(Pageable pageable, Integer addressId) {
        return null;
    }

    public Page<PromotionShortDTO> findAllByCity(Pageable pageable, String city) {
        return null;
    }

    public Page<PromotionShortDTO> findAll(Pageable pageable) {
        return new PageImpl<>(promotionRepository.findAll(pageable).stream()
                .map(promotion -> promotionDtoConvert.convertToPromotionShortDTO(promotion, imageService
                        .findImageByPromotionId(promotion.getId()))).toList());
    }

    public PromotionDTO findById(Integer promotionId) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isPresent()) {
            Promotion promotion = promotionOptional.get();
            return promotionDtoConvert.createPromotionDTO(promotion);
        }
        log.error("Item from promotion table not found, promotionId={}", promotionId);
        return null;
    }

    @Transactional
    public PromotionDTO save(PromotionDTO promotionDTO) {
        return Optional.of(promotionDtoConvert.convertToPromotion(promotionDTO))
                .map(promotionRepository::save)
                .map(promotion -> promotionDtoConvert.convertToPromotionDTO(promotion, imageService
                        .findImageByPromotionId(promotion.getId()))).orElseThrow();
    }

    @Transactional
    public PromotionDTO update(PromotionDTO promotionDTO) {
        Validate.notNull(promotionDTO.getId(), "Field id can't be null");
        Promotion promotion = promotionRepository.findById(promotionDTO.getId()).orElse(null);
        if (promotion != null) {
            return Optional.of(promotionDtoConvert.convertToPromotion(promotionDTO))
                    .map(promotionRepository::save)
                    .map(promotionDtoConvert::createPromotionDTO).orElseThrow();
        }
        log.error("Item from promotion table not found, promotionId={}", promotionDTO.getId());
        return null;
    }

    @Transactional
    public PromotionDTO delete(Integer promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId).orElse(null);
        if (promotion != null) {
            promotion.setState(State.COMPLETED);
            promotionRepository.save(promotion);
            return promotionDtoConvert.createPromotionDTO(promotion);
        }
        log.error("Item from promotion table not found, promotionId={}", promotionId);
        return null;
    }
}
