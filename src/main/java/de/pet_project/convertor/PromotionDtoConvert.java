package de.pet_project.convertor;

import de.pet_project.domain.Promotion;
import de.pet_project.domain.enums.State;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import de.pet_project.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class PromotionDtoConvert {
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public PromotionDTO convertToPromotionDTO(Promotion promotion){
        PromotionDTO promotionDTO = modelMapper.map(promotion, PromotionDTO.class);
        promotionDTO.setState(promotion.getState().name());
        return promotionDTO;
    }

    public PromotionShortDTO convertToPromotionShortDTO(Promotion promotion){
        PromotionShortDTO promotionShortDTO = modelMapper.map(promotion, PromotionShortDTO.class);
        promotionShortDTO.setState(promotion.getState().name());
        return promotionShortDTO;
    }

    public PromotionDTO convertToPromotion(PromotionDTO promotionDTO){
        Promotion promotion = modelMapper.map(promotionDTO, Promotion.class);
        Optional.ofNullable(promotionDTO.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> promotion.setImage(image.getOriginalFilename()));//toString
        promotion.setImage(promotion.getImage());
        promotion.setState(State.valueOf(promotionDTO.getState()));
        uploadImage(promotionDTO.getImage());
        return convertToPromotionDTO(promotion);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
