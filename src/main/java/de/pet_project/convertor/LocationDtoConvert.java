package de.pet_project.convertor;

import de.pet_project.domain.LocationGame;
import de.pet_project.domain.LocationPromotion;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.location.LocationGameDTO;
import de.pet_project.dto.location.LocationPromotionDTO;
import de.pet_project.repository.AddressRepository;
import de.pet_project.repository.GameRepository;
import de.pet_project.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocationDtoConvert {
    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;
    private final PromotionRepository promotionRepository;
    private final AddressRepository addressRepository;

    public LocationGame convertToLocationGame(LocationGameDTO locationGameDTO) {
        LocationGame locationGame = modelMapper.map(locationGameDTO, LocationGame.class);
        locationGame.setGame(gameRepository.findById(locationGameDTO.getGameId()).orElseThrow());
        locationGame.setAddress(addressRepository.findById(locationGameDTO.getAddressId()).orElseThrow());
        return locationGame;
    }

    public LocationGameDTO convertToLocationGameDTO(LocationGame locationGame) {
        LocationGameDTO locationGameDTO = modelMapper.map(locationGame, LocationGameDTO.class);
        locationGameDTO.setGameId(locationGameDTO.getGameId());
        locationGameDTO.setAddressId(locationGameDTO.getAddressId());
        return locationGameDTO;
    }

    public LocationPromotion convertToLocationPromotion(LocationPromotionDTO locationPromotionDTO) {
        LocationPromotion locationPromotion = modelMapper.map(locationPromotionDTO, LocationPromotion.class);
        locationPromotion.setPromotion(promotionRepository.findById(locationPromotionDTO.getPromotionId()).orElseThrow());
        locationPromotion.setAddress(addressRepository.findById(locationPromotionDTO.getAddressId()).orElseThrow());
        return locationPromotion;
    }

    public LocationPromotionDTO convertToLocationPromotionDTO(LocationPromotion locationPromotion) {
        LocationPromotionDTO locationPromotionDTO = modelMapper.map(locationPromotion, LocationPromotionDTO.class);
        locationPromotionDTO.setPromotionId(locationPromotionDTO.getPromotionId());
        locationPromotionDTO.setAddressId(locationPromotionDTO.getAddressId());
        return locationPromotionDTO;
    }
}
