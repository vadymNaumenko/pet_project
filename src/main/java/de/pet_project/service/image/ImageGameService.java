package de.pet_project.service.image;

import de.pet_project.convertor.ImageDtoConvert;
import de.pet_project.domain.enums.State;
import de.pet_project.domain.image.ImageGame;
import de.pet_project.dto.image.ImageGameDTO;
import de.pet_project.repository.image.ImageGameRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageGameService {
    private final ImageGameRepository imageGameRepository;
    private final ImageDtoConvert imageDtoConvert;

    @Transactional
    public ImageGameDTO save(ImageGameDTO imageGameDTO) {
        return Optional.of(imageDtoConvert.convertToImageGame(imageGameDTO)).map(imageGameRepository::save)
                .map(imageDtoConvert::convertToImageGameDTO).orElseThrow();
    }

    @Transactional
    public ImageGameDTO update(ImageGameDTO imageGameDTO) {
        Validate.notNull(imageGameDTO.getId(), "Field id can't be null");
        ImageGame imageGame = imageGameRepository.findById(imageGameDTO.getId()).orElse(null);
        if (imageGame != null) {
            return Optional.of(imageDtoConvert.convertToImageGame(imageGameDTO)).map(imageGameRepository::save)
                    .map(imageDtoConvert::convertToImageGameDTO).orElseThrow();
        }
        log.error("Item from imageGame table not found, imageGameId={}", imageGameDTO.getId());
        return null;
    }

    @Transactional
    public ImageGameDTO delete(Integer imageGameId) {
        ImageGame imageGame = imageGameRepository.findById(imageGameId).orElse(null);
        if (imageGame != null) {
            imageGame.setState(State.COMPLETED);
            imageGameRepository.save(imageGame);
            return imageDtoConvert.convertToImageGameDTO(imageGame);
        }
        log.error("Item from imageGame table not found, imageGameId={}", imageGameId);
        return null;
    }
}
