package de.pet_project.convertor;

import de.pet_project.domain.Promotion;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.promotion.PromotionCreateDTO;
import de.pet_project.dto.promotion.PromotionDTO;
import de.pet_project.dto.promotion.PromotionShortDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionDtoConvert {
    private final ModelMapper modelMapper;

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

    public Promotion convertToPromotionCreate(PromotionCreateDTO promotionCreateDTO){
        Promotion promotion = modelMapper.map(promotionCreateDTO, Promotion.class);
        promotion.setState(State.valueOf(promotionCreateDTO.getState()));
        return promotion;
    }

    public Promotion convertToPromotion(PromotionDTO promotionDTO){
        Promotion promotion = modelMapper.map(promotionDTO, Promotion.class);
        promotion.setState(State.valueOf(promotionDTO.getState()));
        return promotion;
    }
}
