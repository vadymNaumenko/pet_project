package de.pet_project.convertor;

import de.pet_project.domain.image.Image;
import de.pet_project.domain.enums.State;
import de.pet_project.domain.image.ImageGame;
import de.pet_project.domain.image.ImagePromotion;
import de.pet_project.dto.image.ImageDTO;
import de.pet_project.dto.image.ImageGameDTO;
import de.pet_project.dto.image.ImagePromotionDTO;
import de.pet_project.repository.game.GameRepository;
import de.pet_project.repository.image.ImageRepository;
import de.pet_project.repository.promotion.PromotionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageDtoConvert {
    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;
    private final PromotionRepository promotionRepository;
    private final ImageRepository imageRepository;

    /*public <E, D> D convertToDTO(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <E, D> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }*/


    public ImageDTO convertToImageDTO(Image image){
        ImageDTO imageDTO = modelMapper.map(image, ImageDTO.class);
        imageDTO.setState(image.getState().state);
        return imageDTO;
    }

    public ImageDTO createImageDTO(String title, String description, String state) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setTitle(title);
        imageDTO.setDescription(description);
        imageDTO.setState(state);
        return imageDTO;
    }

    public Image convertToImage(ImageDTO imageDTO){
        Image image = modelMapper.map(imageDTO, Image.class);
        image.setState(State.valueOf(imageDTO.getState()));
        return image;
    }

    public ImageGameDTO convertToImageGameDTO(ImageGame imageGame) {
        ImageGameDTO imageGameDTO = modelMapper.map(imageGame, ImageGameDTO.class);
        imageGameDTO.setGameId(imageGame.getGame().getId());
        imageGameDTO.setImageId(imageGame.getImage().getId());
        imageGameDTO.setState(imageGame.getState().state);
        return imageGameDTO;
    }

    public ImageGame convertToImageGame(ImageGameDTO imageGameDTO){
        ImageGame imageGame = modelMapper.map(imageGameDTO, ImageGame.class);
        imageGame.setGame(gameRepository.findById(imageGameDTO.getGameId()).orElseThrow());
        imageGame.setImage(imageRepository.findById(imageGameDTO.getImageId()).orElseThrow());
        imageGame.setState(State.valueOf(imageGameDTO.getState()));
        return imageGame;
    }

    public ImagePromotionDTO convertToImagePromotionDTO(ImagePromotion imagePromotion) {
        ImagePromotionDTO imagePromotionDTO = modelMapper.map(imagePromotion, ImagePromotionDTO.class);
        imagePromotionDTO.setPromotionId(imagePromotion.getPromotion().getId());
        imagePromotionDTO.setImageId(imagePromotion.getImage().getId());
        imagePromotionDTO.setState(imagePromotion.getState().state);
        return imagePromotionDTO;
    }

    public ImagePromotion convertToImagePromotion(ImagePromotionDTO imagePromotionDTO){
        ImagePromotion imagePromotion = modelMapper.map(imagePromotionDTO, ImagePromotion.class);
        imagePromotion.setPromotion(promotionRepository.findById(imagePromotionDTO.getPromotionId())
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found with ID: " +
                        imagePromotionDTO.getPromotionId())));
        imagePromotion.setImage(imageRepository.findById(imagePromotionDTO.getImageId())
                .orElseThrow(() -> new EntityNotFoundException("Image not found with ID: " +
                        imagePromotionDTO.getImageId())));
        imagePromotion.setState(State.valueOf(imagePromotionDTO.getState()));
        return imagePromotion;
    }
}
