package de.pet_project.controller.game;


import de.pet_project.dto.game.LocationGameDTO;
import de.pet_project.service.game.LocationGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/location_games")
public class LocationGameController {
    private final LocationGameService locationGameService;
    @PostMapping()
    public ResponseEntity<LocationGameDTO> save(@RequestBody LocationGameDTO locationGameDTO) {
        LocationGameDTO response = locationGameService.save(locationGameDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<LocationGameDTO> update(@RequestBody LocationGameDTO locationGameDTO) {
        LocationGameDTO response = locationGameService.update(locationGameDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{locationGameId}")
    public ResponseEntity<LocationGameDTO> delete(@PathVariable Integer locationGameId) {
        LocationGameDTO response = locationGameService.delete(locationGameId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}
