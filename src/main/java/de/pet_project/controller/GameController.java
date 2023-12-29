package de.pet_project.controller;

import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import de.pet_project.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {
    public final GameService gameService;
    @GetMapping("/genre/all")
    public List<String> findAllGenres(){
        return gameService.findAllGenre();
    }

    //TODO filter by state???
    @GetMapping("/genre/{genre}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByGenre(@PathVariable Genre genre, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByGenre(genre, pageable);
    }

    @GetMapping("/state/all")
    public List<String> findAllState(){
        return gameService.findAllState();
    }

    @GetMapping("/{id}/state")
    public String findState(@PathVariable Integer id) {
        return gameService.findState(id);
    }

    @GetMapping("/state/{state}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByState(@PathVariable State state, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByState(state, pageable);
    }

    @GetMapping("/numberOfPlayers/all")
    public List<String> findAllNumberOfPlayers(){
        return gameService.findAllNumberOfPlayers();
    }

    //TODO filter by state???
    @GetMapping("/numberOfPlayers/{numberOfPlayers}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByNumberOfPlayers(@PathVariable NumberOfPlayers numberOfPlayers, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByNumberOfPlayers(numberOfPlayers, pageable);
    }

    @GetMapping("/minAge/all")
    public List<String> findAllMinAge(){
        return gameService.findAllMinAge();
    }

    //TODO filter by state???
    @GetMapping("/minAge/{minAge}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByMinAge(@PathVariable MinAge minAge, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByMinAge(minAge, pageable);
    }

    //TODO create top10
    //TODO filter by state???
    @GetMapping("/lenta/page/{pageNum}/{pageSize}")
    public Page<GameDTO> findTopTen(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findTopTen(pageable);
    }

    @GetMapping("/all/page/{pageNum}/{pageSize}")
    public Page<GameShortDTO> findAll(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAll(pageable);
    }

    @GetMapping("/game/more/game{id}")
    public GameDTO findById(@PathVariable Integer id) {
        return gameService.findById(id);
    }

    //TODO ask "make also genre and status"
    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable Integer id) {
        return gameService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
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

    @DeleteMapping("/game/delete/all")
    public void deleteAll() {
        gameService.deleteAll();
    }
}