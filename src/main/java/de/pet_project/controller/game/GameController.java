package de.pet_project.controller.game;

import de.pet_project.dto.game.GameCreateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.dto.game.FilterGameDTO;
import de.pet_project.service.game.GameService;
import lombok.RequiredArgsConstructor;
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
public class GameController {
    private final GameService gameService;

    @PostMapping("filter")
    public Page<GameShortDTO> filter(@RequestBody FilterGameDTO filterGameDTO, @RequestParam("pageNum") Integer pageNum,
                                     @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.filter(filterGameDTO, pageable);
    }


    @GetMapping("/genres")
    public List<String> findAllGenres() {
        return gameService.findAllGenre();
    }

    @GetMapping("/states")
    public List<String> findAllState() {
        return gameService.findAllState();
    }

    @GetMapping("/numberOfPlayers")
    public List<String> findAllNumberOfPlayers() {
        return gameService.findAllNumberOfPlayers();
    }

    @GetMapping("/minAges")
    public List<String> findAllMinAge() {
        return gameService.findAllMinAge();
    }

    //TODO create top10
    @GetMapping("/lenta")
    public Page<GameDTO> findTopTen(@RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findTopTen(pageable);
    }

    @GetMapping()
    public Page<GameShortDTO> findAll(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAll(pageable);
    }

    @GetMapping("/title/{title}")
    public Page<GameShortDTO> findAllByTitle(@PathVariable String title, @RequestParam("pageNum") Integer pageNum,
                                             @RequestParam("pageSize") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findByTitle(title, pageable);
    }

    @GetMapping("/{id}")
    public GameDTO findById(@PathVariable Integer id) {
        return gameService.findById(id);
    }

    @PostMapping()
    public ResponseEntity<GameCreateDTO> save(@RequestBody GameCreateDTO gameCreateDTO) {//
        GameCreateDTO response = gameService.save(gameCreateDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<GameCreateDTO> update(@RequestBody GameCreateDTO gameCreateDTO) {
        GameCreateDTO response = gameService.update(gameCreateDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GameCreateDTO> delete(@PathVariable Integer id) {
        GameCreateDTO response = gameService.delete(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}