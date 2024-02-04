package de.pet_project.service;

import de.pet_project.convertor.GameDtoConvert;
import de.pet_project.dto.game.FilterGameDTO;
import de.pet_project.dto.game.GameCreateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.domain.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // ... (предыдущие методы)

    @Transactional
    public GameDTO save(GameCreateDTO gameCreateDTO){
        log.info("Saving new game: {}", gameCreateDTO.getTitle());

        Game game = gameDtoConvert.convertToGameCreate(gameCreateDTO);
        game = gameRepository.save(game);

        log.info("New game saved successfully: {}", game.getTitle());

        return gameDtoConvert.convertToGameDTO(game);
    }

    @Transactional
    public GameDTO update(GameDTO gameDTO) {
        Validate.notNull(gameDTO.getId(), "Field id can't be null");
        log.info("Updating game with id: {}", gameDTO.getId());

        Optional<Game> gameOptional = gameRepository.findById(gameDTO.getId());
        if (gameOptional.isPresent()) {
            Game game = gameDtoConvert.convertToGame(gameDTO);
            game = gameRepository.save(game);

            log.info("Game updated successfully: {}", game.getTitle());

            return gameDtoConvert.convertToGameDTO(game);
        } else {
            log.error("Game not found for id: {}", gameDTO.getId());
            return null;
        }
    }

    @Transactional
//    @PreAuthorize("hasRole('ADMIN')")
    public GameDTO delete(Integer gameId) {
        log.info("Deleting game with id: {}", gameId);

        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            game.setState(State.COMPLETED);
            gameRepository.save(game);

            log.info("Game deleted successfully: {}", game.getTitle());

            return gameDtoConvert.convertToGameDTO(game);
        } else {
            log.error("Game not found for id: {}", gameId);
            return null;
        }
    }
}
