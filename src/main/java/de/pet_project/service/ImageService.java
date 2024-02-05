package de.pet_project.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Slf4j
@Service
public class ImageService {

    @Value("${app.image.bucket:C:/Users/Вадим/Desktop/final Project/pet_project/images}")
    private String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        log.info("Uploading image: {}", imagePath);

        Path fullImagePath = Path.of(bucket, imagePath);
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            log.info("Image uploaded successfully: {}", imagePath);
        } catch (Exception e) {
            log.error("Error uploading image: {}", imagePath, e);
            throw e;
        }
    }

    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        log.info("Getting image: {}", imagePath);

        Path fullImagePath = Path.of(bucket, imagePath);
        if (Files.exists(fullImagePath)) {
            byte[] imageData = Files.readAllBytes(fullImagePath);

            log.info("Image retrieved successfully: {}", imagePath);

            return Optional.of(imageData);
        } else {
            log.warn("Image not found: {}", imagePath);
            return Optional.empty();
        }
    }

    public String getPath(String avatar) {
        return bucket + avatar;
    }
}
