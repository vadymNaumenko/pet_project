package de.pet_project.controller.image;

import de.pet_project.dto.image.ImageCreateDTO;
import de.pet_project.dto.image.ImageDTO;
import de.pet_project.service.image.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageService imageService;

    //TODO output avatar games by state and by gameId
    @GetMapping(value = "/{id}")
    public Optional<ImageDTO> findImage(@PathVariable Integer id) {
        return imageService.findImage(id);
    }

    @Operation(summary = "Upload image with description",
            description = "This endpoint allows users to upload an image with an associated description.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageCreateDTO> saveImage(@RequestPart("image") MultipartFile image,//TODO ask Marsel ResponseEntity
                                                    @RequestParam("description") String description) {
        ImageCreateDTO response = imageService.saveImage(image, description);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
