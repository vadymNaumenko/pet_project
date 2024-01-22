package de.pet_project.service;

import de.pet_project.convertor.GameDtoConvert;
import de.pet_project.domain.LocationGame;
import de.pet_project.dto.game.FilterGameDTO;
import de.pet_project.dto.game.GameCreateUpdateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.domain.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.location.LocationGameDTO;
import de.pet_project.repository.AddressRepository;
import de.pet_project.repository.GameRepository;
import de.pet_project.repository.LocationGameRepository;
import liquibase.util.Validate;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final LocationGameRepository locationGameRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;


    public Page<GameShortDTO> filter(FilterGameDTO filterGameDTO, Pageable pageable) {
        return new PageImpl<>(locationGameRepository.findAllByFilter(filterGameDTO.getAddressId(),
                        filterGameDTO.getCity(),
                        filterGameDTO.getGenre() != null ? Genre.valueOf(filterGameDTO.getGenre()) : null,
                        filterGameDTO.getState() != null ? State.valueOf(filterGameDTO.getState()) : null,
                        filterGameDTO.getNumberOfPlayers() != null ?
                                NumberOfPlayers.valueOf(filterGameDTO.getNumberOfPlayers()) : null,
                        filterGameDTO.getMinAge() != null ? MinAge.valueOf(filterGameDTO.getMinAge()) : null,
                        pageable).stream()
                .map(gameDtoConvert::convertToGameShortDTO).toList());
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

    public Page<GameShortDTO> findByTitle(String title, Pageable pageable) {
        return new PageImpl<>(gameRepository.findByTitle(title, pageable).stream()
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

    @SneakyThrows
    public String uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
            return image.getOriginalFilename();
        }
        return null;
    }

    @Transactional
    public GameDTO save(GameDTO gameDTO){
        return Optional.of(gameDtoConvert.convertToGame(gameDTO))
                .map(gameRepository::save)
                .map(gameDtoConvert::convertToGameDTO).orElseThrow(() -> new RuntimeException("Failed to save the game"));
    }

    @Transactional
    public GameDTO update(GameDTO gameDTO) {
        Validate.notNull(gameDTO.getId(), "Field id can't be null");
        Game game = gameRepository.findById(gameDTO.getId()).orElse(null);
        if (game != null) {
            game =gameDtoConvert.convertToGame(gameDTO);
            return gameDtoConvert.convertToGameDTO(gameRepository.save(game));
        }
        log.error("Item from game table not found, gameId={}", gameDTO.getId());
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

}
