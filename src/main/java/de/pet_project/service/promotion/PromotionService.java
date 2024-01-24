package de.pet_project.service.promotion;

import de.pet_project.convertor.PromotionDtoConvert;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.promotion.Promotion;
import de.pet_project.domain.enums.State;
import de.pet_project.dto.image.FilterImageDTO;
import de.pet_project.dto.promotion.FilterPromotionDTO;
import de.pet_project.dto.promotion.PromotionCreateDTO;
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

    public Page<PromotionShortDTO> findAllByFilter(FilterPromotionDTO filterPromotionDTO, Pageable pageable) {
        FilterImageDTO fmDTO = new FilterImageDTO();
        fmDTO.setMain(true);
        return new PageImpl<>(locationPromotionRepository.findAllByFilter(filterPromotionDTO.getAddressId(),
                        filterPromotionDTO.getCity(),
                        filterPromotionDTO.getState() != null ? State.valueOf(filterPromotionDTO.getState()) : null,
                        pageable).stream()
                .map(promotion -> promotionDtoConvert.convertToPromotionShortDTO(promotion, imageService
                        .findImageForPromotionByFilter(fmDTO)))
                .toList());
    }

    public Page<PromotionShortDTO> findAll(Pageable pageable) {
        FilterImageDTO fmDTO = new FilterImageDTO();
        fmDTO.setMain(true);
        return new PageImpl<>(promotionRepository.findAll(pageable).stream()
                .map(promotion -> promotionDtoConvert.convertToPromotionShortDTO(promotion, imageService
                        .findImageForGameByFilter(fmDTO)))
                .toList());
    }

    public PromotionDTO findById(Integer promotionId) {
        Optional<Promotion> promotionOptional = promotionRepository.findById(promotionId);
        if (promotionOptional.isPresent()) {
            Promotion promotion = promotionOptional.get();
            return promotionDtoConvert.convertToPromotionDTO(promotion, imageService
                    .findImageByPromotionId(promotion.getId()));
        }
        log.error("Item from promotion table not found, promotionId={}", promotionId);
        return null;
    }

    @Transactional
    public PromotionCreateDTO save(PromotionCreateDTO promotionCreateDTO) {
        return Optional.of(promotionDtoConvert.convertToPromotion(promotionCreateDTO))
                .map(promotionRepository::save)
                .map(promotionDtoConvert::convertToPromotionCreateDTO).orElseThrow();
    }

    @Transactional//TODO
    public PromotionCreateDTO update(PromotionCreateDTO promotionCreateDTO) {
        Validate.notNull(promotionCreateDTO.getId(), "Field id can't be null");
        Promotion promotion = promotionRepository.findById(promotionCreateDTO.getId()).orElse(null);
        if (promotion != null) {
            return Optional.of(promotionDtoConvert.convertToPromotion(promotionCreateDTO))
                    .map(promotionRepository::save)
                    .map(promotionDtoConvert::convertToPromotionCreateDTO).orElseThrow();
        }
        log.error("Item from promotion table not found, promotionId={}", promotionCreateDTO.getId());
        return null;
    }

    @Transactional
    public PromotionCreateDTO delete(Integer promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId).orElse(null);
        if (promotion != null) {
            promotion.setState(State.COMPLETED);
            promotionRepository.save(promotion);
            return promotionDtoConvert.convertToPromotionCreateDTO(promotion);//TODO
        }
        log.error("Item from promotion table not found, promotionId={}", promotionId);
        return null;
    }
}
