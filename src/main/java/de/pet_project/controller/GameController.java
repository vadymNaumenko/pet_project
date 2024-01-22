package de.pet_project.controller;

import de.pet_project.domain.LocationGame;
import de.pet_project.dto.game.GameCreateUpdateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.location.LocationGameDTO;
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
@RequestMapping("/api/v1/games")
public class GameController {
    private final GameService gameService;

    @GetMapping("/address{addressId}/page/{pageNum}/{pageSize}")
    public Page<GameShortDTO> findAllByAddress(@PathVariable Integer addressId, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByAddress(pageable, addressId);
    }

    @GetMapping("/city{city}/page/{pageNum}/{pageSize}")
    public Page<GameShortDTO> findAllByCity(@PathVariable String city, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByCity(pageable, city);
    }


    @GetMapping("/genres")
    public List<String> findAllGenres(){
        return gameService.findAllGenre();
    }

    @GetMapping("/genre/{genre}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByGenre(@PathVariable Genre genre, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByGenre(genre, pageable);
    }

    @GetMapping("/states")
    public List<String> findAllState(){
        return gameService.findAllState();
    }

    @GetMapping("/state/{state}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByState(@PathVariable State state, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByState(state, pageable);
    }

    @GetMapping("/numberOfPlayers")
    public List<String> findAllNumberOfPlayers(){
        return gameService.findAllNumberOfPlayers();
    }

    @GetMapping("/numberOfPlayers/{numberOfPlayers}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByNumberOfPlayers(@PathVariable NumberOfPlayers numberOfPlayers, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByNumberOfPlayers(numberOfPlayers, pageable);
    }

    @GetMapping("/minAges")
    public List<String> findAllMinAge(){
        return gameService.findAllMinAge();
    }

    @GetMapping("/minAge/{minAge}/page/{pageNum}/{pageSize}")//added
    public Page<GameShortDTO> findAllByMinAge(@PathVariable MinAge minAge, @PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAllByMinAge(minAge, pageable);
    }

    //TODO create top10
    @GetMapping("/lenta/page/{pageNum}/{pageSize}")
    public Page<GameDTO> findTopTen(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findTopTen(pageable);
    }

    @GetMapping("/pages/{pageNum}/{pageSize}")
    public Page<GameShortDTO> findAll(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gameService.findAll(pageable);
    }

    @GetMapping("/game{id}")
    public GameDTO findById(@PathVariable Integer id) {
        return gameService.findById(id);
    }

    @GetMapping(value = "/{id}/image")
    public ResponseEntity<byte[]> findImage(@PathVariable Integer id) {
        return gameService.findImage(id)
                .map(content -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .contentLength(content.length)
                        .body(content))
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping()
    public GameCreateUpdateDTO save(@RequestBody GameCreateUpdateDTO gameCreateUpdateDTO) {
        return gameService.save(gameCreateUpdateDTO);
    }

    @PostMapping("location_games")
    public LocationGame save(@RequestBody LocationGameDTO locationGameDTO) {
        return gameService.save(locationGameDTO);
    }

    @PutMapping()
    public ResponseEntity<GameCreateUpdateDTO> update(@RequestBody GameCreateUpdateDTO gameCreateUpdateDTO) {
        GameCreateUpdateDTO response = gameService.update(gameCreateUpdateDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("location_games")
    public ResponseEntity<LocationGame> update(@RequestBody LocationGameDTO locationGameDTO) {
        LocationGame response = gameService.update(locationGameDTO);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<GameDTO> delete(@PathVariable Integer id) {
        GameDTO response = gameService.delete(id);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("location_games{locationGameId}")
    public ResponseEntity<LocationGame> deleteLocationGame(@PathVariable Integer locationGameId) {
        LocationGame response = gameService.deleteLocationGame(locationGameId);
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }
}