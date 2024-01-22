package de.pet_project.service;

import de.pet_project.convertor.LocationDtoConvert;
import de.pet_project.domain.LocationPromotion;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.location.LocationPromotionDTO;
import de.pet_project.repository.LocationGameRepository;
import de.pet_project.repository.LocationPromotionRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationPromotionService {

    private final LocationPromotionRepository locationPromotionRepository;
    private final LocationDtoConvert locationDtoConvert;
    @Transactional
    public LocationPromotionDTO save(LocationPromotionDTO locationPromotionDTO) {
        return Optional.of(locationDtoConvert.convertToLocationPromotion(locationPromotionDTO)).map(locationPromotionRepository::save)
                .map(locationDtoConvert::convertToLocationPromotionDTO).orElseThrow();
    }

    @Transactional
    public LocationPromotionDTO update(LocationPromotionDTO locationPromotionDTO) {
        Validate.notNull(locationPromotionDTO.getId(), "Field id can't be null");
        LocationPromotion locationPromotion = locationPromotionRepository.findById(locationPromotionDTO.getId()).orElse(null);
        if (locationPromotion != null) {
            return Optional.of(locationDtoConvert.convertToLocationPromotion(locationPromotionDTO)).map(locationPromotionRepository::save)
                    .map(locationDtoConvert::convertToLocationPromotionDTO).orElseThrow();
        }
        log.error("Item from locationGame table not found, locationGameId={}", locationPromotionDTO.getId());
        return null;
    }

    @Transactional
    public LocationPromotionDTO deleteLocationPromotion(Integer locationPromotionId) {
        LocationPromotion locationPromotion = locationPromotionRepository.findById(locationPromotionId).orElse(null);
        if (locationPromotion != null) {
            locationPromotion.setState(State.COMPLETED);
            locationPromotionRepository.save(locationPromotion);
            return locationDtoConvert.convertToLocationPromotionDTO(locationPromotion);
        }
        log.error("Item from locationPromotion table not found, locationPromotionId={}", locationPromotionId);
        return null;
    }
}
