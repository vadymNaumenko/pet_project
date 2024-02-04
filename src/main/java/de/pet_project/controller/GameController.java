package de.pet_project.controller;

import de.pet_project.dto.game.GameCreateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.dto.game.FilterGameDTO;
import de.pet_project.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/games")
@Slf4j
public class GameController {
    private final GameService gameService;

    @PostMapping("filter")
    public Page<GameShortDTO> filter(@RequestBody FilterGameDTO filterGameDTO, @RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        log.info("Filtering games with parameters: {}", filterGameDTO);
        return gameService.filter(filterGameDTO, pageable);
    }

    @GetMapping("/genres")
    public List<String> findAllGenres() {
        log.info("Fetching all genres");
        return gameService.findAllGenre();
    }

    @GetMapping("/states")
    public List<String> findAllState() {
        log.info("Fetching all states");
        return gameService.findAllState();
    }

    @GetMapping("/numberOfPlayers")
    public List<String> findAllNumberOfPlayers() {
        log.info("Fetching all number of players");
        return gameService.findAllNumberOfPlayers();
    }

    @GetMapping("/minAges")
    public List<String> findAllMinAge() {
        log.info("Fetching all minimum ages");
        return gameService.findAllMinAge();
    }

    @GetMapping("/lenta")
    public Page<GameDTO> findTopTen(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        log.info("Fetching top 10 games");
        return gameService.findTopTen(pageable);
    }

    @GetMapping()
    public Page<GameShortDTO> findAll(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        log.info("Fetching all games");
        return gameService.findAll(pageable);
    }

    @GetMapping("/title/{title}")
    public Page<GameShortDTO> findAllByTitle(@PathVariable String title, @RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        log.info("Fetching games by title: {}", title);
        return gameService.findByTitle(title, pageable);
    }

    @GetMapping("/game{id}")
    public GameDTO findById(@PathVariable Integer id) {
        log.info("Fetching game by ID: {}", id);
        return gameService.findById(id);
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable Integer id) {
        log.info("Fetching image for game with ID: {}", id);
        return gameService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, path = "/images")
    public String uploadImage(@RequestPart("image") MultipartFile image) {
        log.info("Uploading image");
        return gameService.uploadImage(image);
    }

    @PostMapping()
    public ResponseEntity<GameDTO> save(@RequestBody GameCreateDTO gameCreateDTO) {
        log.info("Saving new game: {}", gameCreateDTO);
        GameDTO response = gameService.save(gameCreateDTO);

        if (response == null) {
            log.warn("Failed to save the game");
            return ResponseEntity.notFound().build();
        }
        log.info("Game saved successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<GameDTO> update(@RequestBody GameDTO gameDTO) {
        log.info("Updating game with ID: {}", gameDTO.getId());
        GameDTO response = gameService.update(gameDTO);
        if (response == null) {
            log.warn("Failed to update the game");
            return ResponseEntity.notFound().build();
        }
        log.info("Game updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GameDTO> delete(@PathVariable Integer id) {
        log.info("Deleting game with ID: {}", id);
        GameDTO response = gameService.delete(id);
        if (response == null) {
            log.warn("Failed to delete the game");
            return ResponseEntity.notFound().build();
        }
        log.info("Game deleted successfully");
        return ResponseEntity.ok(response);
    }
}
