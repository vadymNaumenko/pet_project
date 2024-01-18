package de.pet_project.service;

import de.pet_project.convertor.GameDtoConvert;
import de.pet_project.dto.game.GameCreateUpdateDTO;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameDtoConvert gameDtoConvert;
    private final ImageService imageService;

    public List<String> findAllGenre() {
        return Arrays.stream(Genre.class.getEnumConstants()).map(genre -> genre.genre).toList();
    }

    public Page<GameShortDTO> findAllByGenre(Genre genre, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByGenre(genre, pageable).stream()
                .map(gameDtoConvert::convertToGameShortDTO).toList());
    }

    public List<String> findAllState() {
        return Arrays.stream(State.class.getEnumConstants()).map(state -> state.state).toList();
    }

    public Page<GameShortDTO> findAllByState(State state, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByState(state, pageable).stream()
                .map(gameDtoConvert::convertToGameShortDTO).toList());
    }

    public List<String> findAllNumberOfPlayers() {
        return Arrays.stream(NumberOfPlayers.class.getEnumConstants()).map(number -> number.number).toList();
    }

    public Page<GameShortDTO> findAllByNumberOfPlayers(NumberOfPlayers numberOfPlayers, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByNumberOfPlayers(numberOfPlayers, pageable)
                .stream().map(gameDtoConvert::convertToGameShortDTO).toList());
    }

    public List<String> findAllMinAge() {
        return Arrays.stream(MinAge.class.getEnumConstants()).map(minAge -> minAge.age).toList();
    }

    public Page<GameShortDTO> findAllByMinAge(MinAge minAge, Pageable pageable) {
        return new PageImpl<>(gameRepository.findAllByMinAge(minAge, pageable)
                .stream().map(gameDtoConvert::convertToGameShortDTO).toList());
    }

    public Page<GameDTO> findTopTen(Pageable pageable) {
        return new PageImpl<>(gameRepository.findAll(pageable).stream()
                .map(gameDtoConvert::convertToGameDTO)
                .toList());
    }

    public Page<GameShortDTO> findAll(Pageable pageable) {
        return new PageImpl<>(gameRepository.findAll(pageable).stream()
                .map(gameDtoConvert::convertToGameShortDTO)
                .toList());
    }

    public GameDTO findById(Integer gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            return gameDtoConvert.convertToGameDTO(game);
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

    //TODO game or gameDTO????
    @Transactional

    public GameCreateUpdateDTO save(GameCreateUpdateDTO gameCreateUpdateDTO) {
        return Optional.of(gameDtoConvert.convertToGame(gameCreateUpdateDTO))
                .map(gameRepository::save)
                .map(gameDtoConvert::convertToGameCreateUpdateDTO).orElseThrow();
    }

    //TODO game or gameDTO????
    @Transactional

    public GameCreateUpdateDTO update(GameCreateUpdateDTO gameCreateUpdateDTO) {
        Validate.notNull(gameCreateUpdateDTO.getId(), "Field id can't be null");
        Game game = gameRepository.findById(gameCreateUpdateDTO.getId()).orElse(null);

        if (game != null) {
            game =gameDtoConvert.convertToGame(gameCreateUpdateDTO);
            return gameDtoConvert.convertToGameCreateUpdateDTO(gameRepository.save(game));
        }
        log.error("Item from game table not found, gameId={}", gameCreateUpdateDTO.getId());
        return null;
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public GameDTO delete(Integer gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game != null) {
            game.setState(State.COMPLETED);
            gameRepository.save(game);
            return gameDtoConvert.convertToGameDTO(game);
        }
        log.error("Item from game table not found, gameId={}", gameId);
        return null;
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
