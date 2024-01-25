package de.pet_project.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class ImageService {
    @Value("${app.image.bucket:C:/Users/Вадим/Desktop/final Project/pet_project/images}")
    private String bucket;

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

    public String getPath(String avatar) {
        return bucket+avatar;
    }
}
