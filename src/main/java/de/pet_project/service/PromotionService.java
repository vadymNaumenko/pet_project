package de.pet_project.service;

import de.pet_project.convertor.PromotionDtoConvert;
import de.pet_project.domain.enums.State;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import de.pet_project.domain.Promotion;
import de.pet_project.repository.PromotionRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository promotionRepository;
    private final PromotionDtoConvert promotionDtoConvert;
    private final ImageService imageService;

    public Page<PromotionShortDTO> findAll(Pageable pageable) {
        return new PageImpl<>(promotionRepository.findAll(pageable).stream()
                .map(promotionDtoConvert::convertToPromotionShortDTO)
                .toList());
    }

    public PromotionDTO findById(Integer promotionId) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isPresent()) {
            Promotion promotion = promotionOptional.get();
            return promotionDtoConvert.convertToPromotionDTO(promotion);
        }
        log.error("Item from promotion table not found, promotionId={}", promotionId);
        return null;
    }

    public Optional<byte[]> findImage(Integer id) {
        return promotionRepository.findById(id)
                .map(Promotion::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
    public PromotionDTO save(PromotionDTO promotionDTO) {
        return Optional.of(promotionDtoConvert.convertToPromotion(promotionDTO)).orElseThrow();
    }

    @Transactional
    public PromotionDTO update(PromotionDTO promotionDTO) {
        Validate.notNull(promotionDTO.getId(), "Field id can't be null");
        Promotion promotion = promotionRepository.findById(promotionDTO.getId()).orElse(null);
        if (promotion != null) {
            return promotionDtoConvert.convertToPromotion(promotionDTO);
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
            return promotionDtoConvert.convertToPromotionDTO(promotion);
        }
        log.error("Item from promotion table not found, promotionId={}", promotionId);
        return null;
    }
}
