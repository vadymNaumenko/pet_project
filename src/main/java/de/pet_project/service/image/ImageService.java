package de.pet_project.service.image;

import de.pet_project.convertor.ImageDtoConvert;
import de.pet_project.dto.image.FilterImageDTO;
import de.pet_project.dto.image.ImageCreateDTO;
import de.pet_project.dto.image.ImageDTO;
import de.pet_project.repository.image.ImageGameRepository;
import de.pet_project.repository.image.ImagePromotionRepository;
import de.pet_project.repository.image.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageDtoConvert imageDtoConvert;
    private final ImageRepository imageRepository;
    private final ImageGameRepository imageGameRepository;
    private final ImagePromotionRepository imagePromotionRepository;

    @Value("${app.image.bucket:C:/Users/Вадим/Desktop/final Project/pet_project/images}")
    private String bucket;

    @Transactional
    public List<ImageDTO> findImageByGameId(Integer gameId) {
        return imageGameRepository.findAllByGameId(gameId).stream().map(image -> imageDtoConvert
                .convertToImageDTO(image, getImage(image.getTitle()))).toList();
    }

    public ImageDTO findImageForGameByFilter(FilterImageDTO filterImageDTO) {
        return  imageGameRepository.findImageByFilter(filterImageDTO.getEntityId(), filterImageDTO.isMain())
                .map(image -> imageDtoConvert
                        .convertToImageDTO(image, getImage(image.getTitle())))
                .orElseThrow(() -> new EntityNotFoundException("Image not found for entityId: "
                        + filterImageDTO.getEntityId() + " and isMain: " + filterImageDTO.isMain()));
    }

    @Transactional
    public List<ImageDTO> findImageByPromotionId(Integer promotionId) {
        return imagePromotionRepository.findAllByPromotionId(promotionId).stream().map(image -> imageDtoConvert
                .convertToImageDTO(image, getImage(image.getTitle()))).toList();
    }

    public ImageDTO findImageForPromotionByFilter(FilterImageDTO filterImageDTO) {
        return  imagePromotionRepository.findImageByFilter(filterImageDTO.getEntityId(), filterImageDTO.isMain())
                .map(image -> imageDtoConvert
                        .convertToImageDTO(image, getImage(image.getTitle())))
                .orElseThrow(() -> new EntityNotFoundException("Image not found for entityId: "
                        + filterImageDTO.getEntityId() + " and isMain: " + filterImageDTO.isMain()));
    }

    public Optional<ImageDTO> findImage(Integer id) {
        return imageRepository.findById(id).map(image -> imageDtoConvert
                .convertToImageDTO(image, getImage(image.getTitle())));
    }


    @SneakyThrows
    public ImageCreateDTO saveImage(MultipartFile image, String description) {
        if (!image.isEmpty()) {
            upload(image.getOriginalFilename(), image.getInputStream());
            return Optional.of(imageDtoConvert.convertToImage(imageDtoConvert
                            .createImageCreateDTO(image.getOriginalFilename(), description)))
                    .map(imageRepository::save).map(imageDtoConvert::convertToImageCreateDTO)
                    .orElseThrow(() -> new RuntimeException("Failed to save the image with description: " + description));

        }
        log.error("The image was not saved");
        return null;
    }



    /* *********************************************************************************************************** */
//TODO
    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket,imagePath);
        try(content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath,content.readAllBytes(), StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath){
        Path fullImagePath = Path.of(bucket,imagePath);
        return Files.exists(fullImagePath)? Optional.of(Files.readAllBytes(fullImagePath)) : Optional.empty();
    }

    public byte[] getImage(String title) {
        return Optional.of(title).filter(StringUtils::hasText).flatMap(this::get)
                .orElseThrow(() -> new IllegalStateException("Failed to get byte array for title: " + title));
    }

    /*@Transactional
    public Optional<byte[]> findImage(Integer id) {
        return imageRepository.findById(id)
                .map(Image::getTitle)
                .filter(StringUtils::hasText)
                .flatMap(this::get);
    }*/
}
