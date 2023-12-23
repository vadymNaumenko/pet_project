package de.pet_project.controller;

import de.pet_project.controller.dto.game.GameDTO;
import de.pet_project.controller.dto.game.GameShortDTO;
import de.pet_project.domain.Genre;
import de.pet_project.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    public GameService gameService;

    @GetMapping("/genres/all")
    public List<Genre> getAllGenres(){
        return gameService.getAllGenres();
    }

    //TODO create top10
    @GetMapping("/lenta/page{pageNum}/{pageSize}")
    public Page<GameDTO> findTopTen(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findTopTen(pageable);
    }

    @GetMapping("/all/page{pageNum}/{pageSize}")
    public Page<GameShortDTO> findAll(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAll(pageable);
    }

    @GetMapping("/game/more/game{id}")
    public GameDTO findById(@PathVariable Integer id) {
        return gameService.findById(id);
    }

    @PostMapping("/game/createNew")
    public GameDTO create(@RequestBody GameDTO gameDTO) {
        return gameService.save(gameDTO);
    }

    @PutMapping("/game/update")
    public ResponseEntity<GameDTO> update(@RequestBody GameDTO gameDTO) {
        GameDTO response = gameService.update(gameDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/game/delete/{id}")
    public ResponseEntity<GameDTO> delete(@PathVariable Integer id) {
        GameDTO response = gameService.delete(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}