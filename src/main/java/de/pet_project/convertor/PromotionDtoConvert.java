package de.pet_project.convertor;

import de.pet_project.domain.enums.State;
import de.pet_project.domain.promotion.Promotion;
import de.pet_project.dto.image.ImageDTO;
import de.pet_project.dto.promotion.PromotionCreateDTO;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PromotionDtoConvert {
    private final ModelMapper modelMapper;


    public PromotionCreateDTO convertToPromotionCreateDTO(Promotion promotion){
        PromotionCreateDTO promotionCreateDTO = modelMapper.map(promotion, PromotionCreateDTO.class);
        promotionCreateDTO.setState(promotion.getState().name());
        return promotionCreateDTO;
    }

    public PromotionDTO convertToPromotionDTO(Promotion promotion, List<ImageDTO> images){
        PromotionDTO promotionDTO = modelMapper.map(promotion, PromotionDTO.class);
        promotionDTO.setImages(images);
        promotionDTO.setState(promotion.getState().name());
        return promotionDTO;
    }

    public PromotionShortDTO convertToPromotionShortDTO(Promotion promotion, ImageDTO image){
        PromotionShortDTO promotionShortDTO = modelMapper.map(promotion, PromotionShortDTO.class);
        promotionShortDTO.setImage(image);
        promotionShortDTO.setState(promotion.getState().name());
        return promotionShortDTO;
    }

    public Promotion convertToPromotion(PromotionCreateDTO promotionCreateDTO){
        Promotion promotion = modelMapper.map(promotionCreateDTO, Promotion.class);
        promotion.setState(State.valueOf(promotionCreateDTO.getState()));
        return promotion;
    }
}
