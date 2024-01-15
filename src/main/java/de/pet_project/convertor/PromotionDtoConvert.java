package de.pet_project.convertor;

import de.pet_project.domain.Promotion;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.promotion.PromotionCreateUpdateDTO;
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

    public PromotionCreateUpdateDTO convertToPromotionCreateUpdateDTO(Promotion promotion){
        PromotionCreateUpdateDTO promotionCreateUpdateDTO = modelMapper.map(promotion, PromotionCreateUpdateDTO.class);
        promotionCreateUpdateDTO.setState(promotion.getState().name());
        return promotionCreateUpdateDTO;
    }

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

    public Promotion convertToPromotion(PromotionCreateUpdateDTO promotionCreateUpdateDTO){
        Promotion promotion = modelMapper.map(promotionCreateUpdateDTO, Promotion.class);
        Optional.ofNullable(promotionCreateUpdateDTO.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> promotion.setImage(image.getOriginalFilename()));//toString
        promotion.setImage(promotion.getImage());
        promotion.setState(State.valueOf(promotionCreateUpdateDTO.getState()));
        uploadImage(promotionCreateUpdateDTO.getImage());
        return promotion;
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
