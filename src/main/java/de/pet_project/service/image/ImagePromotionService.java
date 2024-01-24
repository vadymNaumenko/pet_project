package de.pet_project.service.image;

import de.pet_project.convertor.ImageDtoConvert;
import de.pet_project.domain.enums.State;
import de.pet_project.domain.image.ImagePromotion;
import de.pet_project.dto.image.ImagePromotionDTO;
import de.pet_project.repository.image.ImagePromotionRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagePromotionService {
    private final ImagePromotionRepository imagePromotionRepository;
    private final ImageDtoConvert imageDtoConvert;

    @Transactional
    public ImagePromotionDTO save(ImagePromotionDTO imagePromotionDTO) {
        return Optional.of(imageDtoConvert.convertToImagePromotion(imagePromotionDTO)).map(imagePromotionRepository::save)
                .map(imageDtoConvert::convertToImagePromotionDTO).orElseThrow();
    }

    @Transactional
    public ImagePromotionDTO update(ImagePromotionDTO imagePromotionDTO) {
        Validate.notNull(imagePromotionDTO.getId(), "Field id can't be null");
        ImagePromotion imagePromotion = imagePromotionRepository.findById(imagePromotionDTO.getId()).orElse(null);
        if (imagePromotion != null) {
            return Optional.of(imageDtoConvert.convertToImagePromotion(imagePromotionDTO)).map(imagePromotionRepository::save)
                    .map(imageDtoConvert::convertToImagePromotionDTO).orElseThrow();
        }
        log.error("Item from imagePromotion table not found, imagePromotionId={}", imagePromotionDTO.getId());
        return null;
    }

    @Transactional
    public ImagePromotionDTO delete(Integer imagePromotionId) {
        ImagePromotion imagePromotion = imagePromotionRepository.findById(imagePromotionId).orElse(null);
        if (imagePromotion != null) {
            imagePromotion.setDeleted(true);
            imagePromotionRepository.save(imagePromotion);
            return imageDtoConvert.convertToImagePromotionDTO(imagePromotion);
        }
        log.error("Item from imagePromotion table not found, imagePromotionId={}", imagePromotionId);
        return null;
    }
}
