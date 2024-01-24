package de.pet_project.convertor;

import de.pet_project.domain.enums.State;
import de.pet_project.domain.game.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.dto.game.GameCreateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import de.pet_project.dto.image.ImageDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GameDtoConvert {
    private final ModelMapper modelMapper;

    public GameCreateDTO convertToGameCreateDTO(Game game){
        GameCreateDTO gameCreateDTO = modelMapper.map(game, GameCreateDTO.class);
        gameCreateDTO.setGenre(game.getGenre().genre);
        gameCreateDTO.setState(game.getState().state);
        gameCreateDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameCreateDTO.setMinAge(game.getMinAge().age);
        return gameCreateDTO;
    }

    public GameDTO convertToGameDTO(Game game, List<ImageDTO> imageDTOS){
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setImages(imageDTOS);
        gameDTO.setGenre(game.getGenre().genre);
        gameDTO.setState(game.getState().state);
        gameDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameDTO.setMinAge(game.getMinAge().age);
        return gameDTO;
    }

    public GameShortDTO convertToGameShortDTO(Game game, ImageDTO imageDTO){
        GameShortDTO gameShortDTO = modelMapper.map(game, GameShortDTO.class);
        gameShortDTO.setImage(imageDTO);
        gameShortDTO.setState(game.getState().state);
        gameShortDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameShortDTO.setMinAge(game.getMinAge().age);
        return gameShortDTO;
    }

    public Game convertToGame(GameCreateDTO gameCreateDTO){
        Game game = modelMapper.map(gameCreateDTO, Game.class);
        game.setGenre(Genre.valueOf(gameCreateDTO.getGenre()));
        game.setState(State.valueOf(gameCreateDTO.getState()));
        game.setNumberOfPlayers(NumberOfPlayers.valueOf(gameCreateDTO.getNumberOfPlayers()));
        game.setMinAge(MinAge.valueOf(gameCreateDTO.getMinAge()));
        return game;
    }
}
