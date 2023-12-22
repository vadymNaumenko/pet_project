package de.pet_project.service;

import de.pet_project.controller.dto.game.GameDTO;
import de.pet_project.controller.dto.game.GameShortDTO;
import de.pet_project.domain.Game;
import de.pet_project.domain.Genre;
import de.pet_project.repository.GameRepository;
import liquibase.util.Validate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class GameService {
    @Autowired
    public GameRepository gameRepository;


    public Page<GameDTO> findTopTen(Pageable pageable) {
        return new PageImpl<>(gameRepository.findAll(pageable).stream()
                .map(GameDTO::getInstance)
                .toList());
    }

    public Page<GameShortDTO> findAll(Pageable pageable) {
        return new PageImpl<>(gameRepository.findAll(pageable).stream()
                .map(GameShortDTO::getInstance)
                .toList());
    }

    public GameDTO findById(Integer gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            return GameDTO.getInstance(game);
        }
        log.error("Item from game table not found, gameId={}", gameId);
        return null;
    }

    @Transactional
    public GameDTO save(GameDTO gameDTO) {
        //return fillAndSave(gameDTO, new Game());
        return Optional.of(fillAndSave(gameDTO, new Game())).orElseThrow();
    }

    private GameDTO fillAndSave(GameDTO gameDTO, Game game) {
        game.setImage(gameDTO.getImage());
        game.setTitle(gameDTO.getTitle());
        game.setPrice(gameDTO.getPrice());
        game.setGenre(Genre.valueOf(gameDTO.getGenre()));
        game.setSession(gameDTO.getSession());
        game.setNumberOfPlayers(gameDTO.getNumberOfPlayers());
        game.setMinAge(gameDTO.getMinAge());
        game.setDescription(gameDTO.getDescription());
        game.setReleaseDate(gameDTO.getReleaseDate());
        game = gameRepository.save(game);
        return GameDTO.getInstance(game);
    }

    @Transactional
    public GameDTO update(GameDTO gameDTO) {
        Validate.notNull(gameDTO.getId(), "Field id can't be null");
        Game game = gameRepository.findById(gameDTO.getId()).orElse(null);
        if (game != null) {
            return fillAndSave(gameDTO, game);
        }
        log.error("Item from game table not found, gameId={}", gameDTO.getId());
        return null;
    }

    @Transactional
    public GameDTO delete(Integer gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game != null) {
            gameRepository.delete(game);
            return GameDTO.getInstance(game);
        }
        log.error("Item from game table not found, gameId={}", gameId);
        return null;
    }

}
