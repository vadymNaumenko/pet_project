package de.pet_project.service;

import de.pet_project.convertor.PromotionDtoConvert;
import de.pet_project.domain.LocationGame;
import de.pet_project.domain.LocationPromotion;
import de.pet_project.domain.Promotion;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.location.LocationPromotionDTO;
import de.pet_project.dto.promotion.PromotionCreateUpdateDTO;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import de.pet_project.repository.AddressRepository;
import de.pet_project.repository.LocationPromotionRepository;
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
    private final LocationPromotionRepository locationPromotionRepository;
    private final AddressRepository addressRepository;

    public Page<PromotionShortDTO> findAllByAddress(Pageable pageable, Integer addressId) {
        return new PageImpl<>(locationPromotionRepository.findAllByAddress(pageable, addressId).stream()
                .map(promotionDtoConvert::convertToPromotionShortDTO)
                .toList());
    }

    public Page<PromotionShortDTO> findAllByCity(Pageable pageable, String city) {
        return new PageImpl<>(locationPromotionRepository.findAllByCity(pageable, city).stream()
                .map(promotionDtoConvert::convertToPromotionShortDTO)
                .toList());
    }

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
    public PromotionCreateUpdateDTO save(PromotionCreateUpdateDTO promotionCreateUpdateDTO) {
        return Optional.of(promotionDtoConvert.convertToPromotion(promotionCreateUpdateDTO))
                .map(promotionRepository::save)
                .map(promotionDtoConvert::convertToPromotionCreateUpdateDTO).orElseThrow();
    }

    //TODO???
    @Transactional
    public LocationPromotion save(LocationPromotionDTO locationPromotionDTO) {
        LocationPromotion locationPromotion = new LocationPromotion();
        locationPromotion.setPromotion(promotionRepository.findById(locationPromotionDTO.getPromotionId()).orElseThrow());
        locationPromotion.setAddress(addressRepository.findById(locationPromotionDTO.getAddressId()).orElseThrow());
        locationPromotion.setState(State.valueOf(locationPromotionDTO.getState()));
        return locationPromotionRepository.save(locationPromotion);
    }

    @Transactional
    public PromotionCreateUpdateDTO update(PromotionCreateUpdateDTO promotionCreateUpdateDTO) {
        Validate.notNull(promotionCreateUpdateDTO.getId(), "Field id can't be null");
        Promotion promotion = promotionRepository.findById(promotionCreateUpdateDTO.getId()).orElse(null);
        if (promotion != null) {
            promotion =promotionDtoConvert.convertToPromotion(promotionCreateUpdateDTO);
            return promotionDtoConvert.convertToPromotionCreateUpdateDTO(promotionRepository.save(promotion));
        }
        log.error("Item from promotion table not found, promotionId={}", promotionCreateUpdateDTO.getId());
        return null;
    }

    //TODO????
    @Transactional
    public LocationPromotion update(LocationPromotionDTO locationPromotionDTO) {
        Validate.notNull(locationPromotionDTO.getId(), "Field id can't be null");
        LocationPromotion locationPromotion = locationPromotionRepository.findById(locationPromotionDTO.getId()).orElse(null);
        if (locationPromotion != null) {
            locationPromotion.setPromotion(promotionRepository.findById(locationPromotionDTO.getPromotionId()).orElseThrow());
            locationPromotion.setAddress(addressRepository.findById(locationPromotionDTO.getAddressId()).orElseThrow());
            locationPromotion.setState(State.valueOf(locationPromotionDTO.getState()));
            return locationPromotionRepository.save(locationPromotion);
        }
        log.error("Item from locationGame table not found, locationGameId={}", locationPromotionDTO.getId());
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

    @Transactional
    public LocationPromotion deleteLocationPromotion(Integer locationPromotionId) {
        LocationPromotion locationPromotion = locationPromotionRepository.findById(locationPromotionId).orElse(null);
        if (locationPromotion != null) {
            locationPromotion.setState(State.COMPLETED);
            return locationPromotionRepository.save(locationPromotion);
        }
        log.error("Item from locationPromotion table not found, locationPromotionId={}", locationPromotionId);
        return null;
    }
}
