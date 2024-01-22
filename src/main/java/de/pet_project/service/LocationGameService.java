package de.pet_project.service;

import de.pet_project.convertor.LocationDtoConvert;
import de.pet_project.domain.LocationGame;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.location.LocationGameDTO;
import de.pet_project.repository.LocationGameRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationGameService {

    private final LocationGameRepository locationGameRepository;
    private final LocationDtoConvert locationDtoConvert;

    @Transactional
    public LocationGameDTO save(LocationGameDTO locationGameDTO) {
        return Optional.of(locationDtoConvert.convertToLocationGame(locationGameDTO)).map(locationGameRepository::save)
                .map(locationDtoConvert::convertToLocationGameDTO).orElseThrow();
    }

    @Transactional
    public LocationGameDTO update(LocationGameDTO locationGameDTO) {
        Validate.notNull(locationGameDTO.getId(), "Field id can't be null");
        LocationGame locationGame = locationGameRepository.findById(locationGameDTO.getId()).orElse(null);
        if (locationGame != null) {
            return Optional.of(locationDtoConvert.convertToLocationGame(locationGameDTO)).map(locationGameRepository::save)
                    .map(locationDtoConvert::convertToLocationGameDTO).orElseThrow();
        }
        log.error("Item from locationGame table not found, locationGameId={}", locationGameDTO.getId());
        return null;
    }

    @Transactional
    public LocationGameDTO deleteLocationGame(Integer locationGameId) {
        LocationGame locationGame = locationGameRepository.findById(locationGameId).orElse(null);
        if (locationGame != null) {
            locationGame.setState(State.COMPLETED);
            locationGameRepository.save(locationGame);
            return locationDtoConvert.convertToLocationGameDTO(locationGame);
        }
        log.error("Item from locationGame table not found, locationGameId={}", locationGameId);
        return null;
    }
}
