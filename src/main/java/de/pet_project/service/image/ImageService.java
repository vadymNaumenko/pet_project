package de.pet_project.service.image;

import de.pet_project.convertor.ImageDtoConvert;
import de.pet_project.domain.game.Game;
import de.pet_project.domain.image.Image;
import de.pet_project.dto.image.ImageDTO;
import de.pet_project.repository.image.ImageGameRepository;
import de.pet_project.repository.image.ImagePromotionRepository;
import de.pet_project.repository.image.ImageRepository;
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
        return imageGameRepository.findAllByGameId(gameId).stream().map(imageDtoConvert::convertToImageDTO).toList();
    }

    @Transactional
    public List<ImageDTO> findImageByPromotionId(Integer promotionId) {
        return imagePromotionRepository.findAllByPromotionId(promotionId).stream().map(imageDtoConvert::convertToImageDTO)
                .toList();
    }

    @Transactional
    public Optional<byte[]> findImage(Integer id) {
        return imageRepository.findById(id)
                .map(Image::getTitle)
                .filter(StringUtils::hasText)
                .flatMap(this::get);
    }

    @SneakyThrows
    public ImageDTO saveImage(MultipartFile image, String description, String state) {
        if (!image.isEmpty()) {
            upload(image.getOriginalFilename(), image.getInputStream());
            return Optional.of(imageDtoConvert.convertToImage(imageDtoConvert
                            .createImageDTO(image.getOriginalFilename(), description, state)))
                    .map(imageRepository::save).map(imageDtoConvert::convertToImageDTO)
                    .orElseThrow(() -> new RuntimeException("Failed to save the image"));
        }
        log.error("The image was not saved");
        return null;
    }

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
}
