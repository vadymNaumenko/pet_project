package de.pet_project.convertor;

import de.pet_project.domain.Game;
import de.pet_project.domain.enums.State;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.dto.address.AddressDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class GameDtoConvert {
    private final ModelMapper modelMapper;
    private final ImageService imageService;

    public GameDTO convertToGameDTO(Game game){
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setGenre(game.getGenre().name());
        gameDTO.setState(game.getState().name());
        gameDTO.setNumberOfPlayers(game.getNumberOfPlayers().name());
        gameDTO.setMinAge(game.getMinAge().name());
        return gameDTO;
    }

    public GameShortDTO convertToGameShortDTO(Game game){
        GameShortDTO gameShortDTO = modelMapper.map(game, GameShortDTO.class);
        gameShortDTO.setState(game.getState().name());
        gameShortDTO.setNumberOfPlayers(game.getNumberOfPlayers().name());
        gameShortDTO.setMinAge(game.getMinAge().name());
        return gameShortDTO;
    }

    public GameDTO convertToGame(GameDTO gameDTO){
        Game game = modelMapper.map(gameDTO, Game.class);
        Optional.ofNullable(gameDTO.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> game.setImage(image.getOriginalFilename()));//toString
        game.setImage(game.getImage());
        game.setGenre(Genre.valueOf(gameDTO.getGenre()));
        game.setState(State.valueOf(gameDTO.getState()));
        game.setNumberOfPlayers(NumberOfPlayers.valueOf(gameDTO.getNumberOfPlayers()));
        game.setMinAge(MinAge.valueOf(gameDTO.getMinAge()));
        uploadImage(gameDTO.getImage());
        return convertToGameDTO(game);
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
    }
}
