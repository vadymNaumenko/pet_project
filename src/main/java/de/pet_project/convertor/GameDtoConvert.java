package de.pet_project.convertor;

import de.pet_project.domain.enums.State;
import de.pet_project.domain.game.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
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

    public GameDTO createGameDTO(Game game){
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setGenre(game.getGenre().genre);
        gameDTO.setState(game.getState().state);
        gameDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameDTO.setMinAge(game.getMinAge().age);
        return gameDTO;
    }

    public GameDTO convertToGameDTO(Game game, List<ImageDTO> images){
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setImages(images);
        gameDTO.setGenre(game.getGenre().genre);
        gameDTO.setState(game.getState().state);
        gameDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameDTO.setMinAge(game.getMinAge().age);
        return gameDTO;
    }

    public GameShortDTO convertToGameShortDTO(Game game, List<ImageDTO> images){
        GameShortDTO gameShortDTO = modelMapper.map(game, GameShortDTO.class);
        gameShortDTO.setImages(images);
        gameShortDTO.setState(game.getState().state);
        gameShortDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameShortDTO.setMinAge(game.getMinAge().age);
        return gameShortDTO;
    }

    public Game convertToGame(GameDTO gameDTO){
        Game game = modelMapper.map(gameDTO, Game.class);
        game.setGenre(Genre.valueOf(gameDTO.getGenre()));
        game.setState(State.valueOf(gameDTO.getState()));
        game.setNumberOfPlayers(NumberOfPlayers.valueOf(gameDTO.getNumberOfPlayers()));
        game.setMinAge(MinAge.valueOf(gameDTO.getMinAge()));
        return game;
    }
}
