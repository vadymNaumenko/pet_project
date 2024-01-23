package de.pet_project.controller.image;

import de.pet_project.dto.image.ImageGameDTO;
import de.pet_project.service.image.ImageGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/images_games")
public class ImageGameController {
    private final ImageGameService imageGameService;
    @PostMapping()
    public ResponseEntity<ImageGameDTO> save(@RequestBody ImageGameDTO imageGameDTO) {
        ImageGameDTO response = imageGameService.save(imageGameDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ImageGameDTO> update(@RequestBody ImageGameDTO imageGameDTO) {
        ImageGameDTO response = imageGameService.update(imageGameDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{imageGameId}")
    public ResponseEntity<ImageGameDTO> delete(@PathVariable Integer imageGameId) {
        ImageGameDTO response = imageGameService.delete(imageGameId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
