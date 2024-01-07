package de.pet_project.service;

import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.domain.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import de.pet_project.repository.GameRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    public final GameRepository gameRepository;
    public final ImageService imageService;

    public List<String> findAllGenre() {
        return Arrays.stream(Genre.class.getEnumConstants()).map(genre -> genre.genre).toList();
    }

    public Page<GameShortDTO> findAllByGenre(Genre genre, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByGenre(genre, pageable).stream()
                .map(GameShortDTO::getInstance).toList());
    }

    public List<String> findAllState() {
        return Arrays.stream(State.class.getEnumConstants()).map(state -> state.state).toList();
    }

    public String findState(Integer id) {
        return gameRepository.findState(id).state;
    }

    public Page<GameShortDTO> findAllByState(State state, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByState(state, pageable).stream()
                .map(GameShortDTO::getInstance).toList());
    }

    public List<String> findAllNumberOfPlayers() {
        return Arrays.stream(NumberOfPlayers.class.getEnumConstants()).map(number -> number.number).toList();
    }

    public Page<GameShortDTO> findAllByNumberOfPlayers(NumberOfPlayers numberOfPlayers, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByNumberOfPlayers(numberOfPlayers, pageable)
                .stream().map(GameShortDTO::getInstance).toList());
    }

    public List<String> findAllMinAge() {
        return Arrays.stream(MinAge.class.getEnumConstants()).map(minAge -> minAge.age).toList();
    }

    public Page<GameShortDTO> findAllByMinAge(MinAge minAge, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByMinAge(minAge, pageable)
                .stream().map(GameShortDTO::getInstance).toList());
    }

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

    public Optional<byte[]> findImage(Integer id) {
        return gameRepository.findById(id)
                .map(Game::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public GameDTO save(GameDTO gameDTO) {
        //return fillAndSave(gameDTO, new Game());
        return Optional.of(fillAndSave(gameDTO, new Game())).orElseThrow();
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
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
//    @PreAuthorize("hasRole('ADMIN')")
    public GameDTO delete(Integer gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game != null) {
            gameRepository.delete(game);
            return GameDTO.getInstance(game);
        }
        log.error("Item from game table not found, gameId={}", gameId);
        return null;
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll() {
        gameRepository.deleteAll();
    }

    private GameDTO fillAndSave(GameDTO gameDTO, Game game) {
        Game finalGame = game;
        Optional.ofNullable(gameDTO.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> finalGame.setImage(image.getOriginalFilename()));//toString
        game.setImage(finalGame.getImage());
        game.setTitle(gameDTO.getTitle());
        game.setPrice(gameDTO.getPrice());
        game.setGenre(Genre.valueOf(gameDTO.getGenre()));
        game.setState(State.valueOf(gameDTO.getState()));
        game.setSession(gameDTO.getSession());
        game.setNumberOfPlayers(NumberOfPlayers.valueOf(gameDTO.getNumberOfPlayers()));
        game.setMinAge(MinAge.valueOf(gameDTO.getMinAge()));
        game.setDescription(gameDTO.getDescription());
        game.setReleaseDate(gameDTO.getReleaseDate());
        game = gameRepository.save(game);
        uploadImage(gameDTO.getImage());
        return GameDTO.getInstance(game);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
