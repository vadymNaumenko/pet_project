package de.pet_project.controller;

import de.pet_project.dto.location.LocationGameDTO;
import de.pet_project.service.LocationGameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/location_games")
@Slf4j
public class LocationGameController {
    private final LocationGameService locationGameService;

    @PostMapping()
    public ResponseEntity<LocationGameDTO> save(@RequestBody LocationGameDTO locationGameDTO) {
        log.info("Saving new location game: {}", locationGameDTO);
        LocationGameDTO response = locationGameService.save(locationGameDTO);
        if (response == null) {
            log.warn("Failed to save location game");
            return ResponseEntity.notFound().build();
        }
        log.info("Location game saved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<LocationGameDTO> update(@RequestBody LocationGameDTO locationGameDTO) {
        log.info("Updating location game with ID: {}", locationGameDTO.getId());
        LocationGameDTO response = locationGameService.update(locationGameDTO);
        if (response == null) {
            log.warn("Failed to update the location game");
            return ResponseEntity.notFound().build();
        }
        log.info("Location game updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{locationGameId}")
    public ResponseEntity<LocationGameDTO> deleteLocationGame(@PathVariable Integer locationGameId) {
        log.info("Deleting location game with ID: {}", locationGameId);
        LocationGameDTO response = locationGameService.deleteLocationGame(locationGameId);
        if (response == null) {
            log.warn("Failed to delete the location game");
            return ResponseEntity.notFound().build();
        }
        log.info("Location game deleted successfully");
        return ResponseEntity.ok(response);
    }
}
