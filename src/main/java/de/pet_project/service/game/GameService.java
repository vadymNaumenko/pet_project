package de.pet_project.service.game;

import de.pet_project.convertor.GameDtoConvert;
import de.pet_project.domain.enums.State;
import de.pet_project.dto.game.FilterGameDTO;
import de.pet_project.dto.game.GameCreateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.domain.game.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.dto.image.FilterImageDTO;
import de.pet_project.repository.game.GameRepository;
import de.pet_project.repository.game.LocationGameRepository;
import de.pet_project.service.image.ImageService;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GameDtoConvert gameDtoConvert;
    private final LocationGameRepository locationGameRepository;
    private final ImageService imageService;


    public Page<GameShortDTO> filter(FilterGameDTO filterGameDTO, Pageable pageable) {
        FilterImageDTO fmDTO = new FilterImageDTO();
        fmDTO.setMain(true);
        return new PageImpl<>(locationGameRepository.findAllByFilter(filterGameDTO.getAddressId(),
                        filterGameDTO.getCity(),
                        filterGameDTO.getGenre() != null ? Genre.valueOf(filterGameDTO.getGenre()) : null,
                        filterGameDTO.getState() != null ? State.valueOf(filterGameDTO.getState()) : null,
                        filterGameDTO.getNumberOfPlayers() != null ?
                                NumberOfPlayers.valueOf(filterGameDTO.getNumberOfPlayers()) : null,
                        filterGameDTO.getMinAge() != null ? MinAge.valueOf(filterGameDTO.getMinAge()) : null,
                        pageable).stream()
                .map(game -> gameDtoConvert.convertToGameShortDTO(game, imageService
                        .findImageForGameByFilter(fmDTO)))
                .toList());
    }

    public List<String> findAllGenre() {
        return Arrays.stream(Genre.class.getEnumConstants()).map(genre -> genre.genre).toList();
    }

    public List<String> findAllState() {
        return Arrays.stream(State.class.getEnumConstants()).map(state -> state.state).toList();
    }

    public List<String> findAllNumberOfPlayers() {
        return Arrays.stream(NumberOfPlayers.class.getEnumConstants()).map(number -> number.number).toList();
    }

    public List<String> findAllMinAge() {
        return Arrays.stream(MinAge.class.getEnumConstants()).map(minAge -> minAge.age).toList();
    }

    public Page<GameDTO> findTopTen(Pageable pageable) {//TODO output only one image avatar!!!!
        return new PageImpl<>(gameRepository.findAll(pageable).stream()
                .map(game -> gameDtoConvert.convertToGameDTO(game, imageService.findImageByGameId(game.getId())))
                .toList());
    }


    public Page<GameShortDTO> findAll(Pageable pageable) {
        FilterImageDTO fmDTO = new FilterImageDTO();
        fmDTO.setMain(true);
        return new PageImpl<>(gameRepository.findAll(pageable).stream()
                .map(game -> gameDtoConvert.convertToGameShortDTO(game, imageService
                        .findImageForGameByFilter(fmDTO)))
                .toList());
    }

    public Page<GameShortDTO> findByTitle(String title, Pageable pageable) {
        FilterImageDTO fmDTO = new FilterImageDTO();
        fmDTO.setMain(true);
        return new PageImpl<>(gameRepository.findByTitle(title, pageable).stream()
                .map(game -> gameDtoConvert.convertToGameShortDTO(game, imageService
                        .findImageForGameByFilter(fmDTO)))
                .toList());
    }

    public GameDTO findById(Integer gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            return gameDtoConvert.convertToGameDTO(game, imageService.findImageByGameId(game.getId()));
        }
        log.error("Item from game table not found, gameId={}", gameId);
        return null;
    }

    @Transactional
    public GameCreateDTO save(GameCreateDTO gameCreateDTO){
        return Optional.of(gameDtoConvert.convertToGame(gameCreateDTO))
                .map(gameRepository::save)
                .map(gameDtoConvert::convertToGameCreateDTO).orElseThrow(() -> new RuntimeException("Failed to save the game"));
    }

    @Transactional
    public GameCreateDTO update(GameCreateDTO gameCreateDTO) {//TODO ?????
        Validate.notNull(gameCreateDTO.getId(), "Field id can't be null");
        Game game = gameRepository.findById(gameCreateDTO.getId()).orElse(null);
        if (game != null) {
            return Optional.of(gameDtoConvert.convertToGame(gameCreateDTO))
                    .map(gameRepository::save)
                    .map(gameDtoConvert::convertToGameCreateDTO).orElseThrow();
        }
        log.error("Item from game table not found, gameId={}", gameCreateDTO.getId());
        return null;
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public GameCreateDTO delete(Integer gameId) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game != null) {
            game.setState(State.COMPLETED);
            gameRepository.save(game);
            return gameDtoConvert.convertToGameCreateDTO(game);//TODO ????
        }
        log.error("Item from game table not found, gameId={}", gameId);
        return null;
    }
}
