package de.pet_project.convertor;

import de.pet_project.domain.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.game.GameCreateDTO;
import de.pet_project.dto.game.GameDTO;
import de.pet_project.dto.game.GameShortDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameDtoConvert {
    private final ModelMapper modelMapper;

    public GameDTO convertToGameDTO(Game game){
        GameDTO gameDTO = modelMapper.map(game, GameDTO.class);
        gameDTO.setGenre(game.getGenre().genre);
        gameDTO.setState(game.getState().state);
        gameDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameDTO.setMinAge(game.getMinAge().age);
        return gameDTO;
    }

    public GameShortDTO convertToGameShortDTO(Game game){
        GameShortDTO gameShortDTO = modelMapper.map(game, GameShortDTO.class);
        gameShortDTO.setState(game.getState().state);
        gameShortDTO.setNumberOfPlayers(game.getNumberOfPlayers().number);
        gameShortDTO.setMinAge(game.getMinAge().age);
        return gameShortDTO;
    }

    public Game convertToGameCreate(GameCreateDTO gameCreateDTO){
        Game game = modelMapper.map(gameCreateDTO, Game.class);
        game.setGenre(Genre.valueOf(gameCreateDTO.getGenre()));
        game.setState(State.valueOf(gameCreateDTO.getState()));
        game.setNumberOfPlayers(NumberOfPlayers.valueOf(gameCreateDTO.getNumberOfPlayers()));
        game.setMinAge(MinAge.valueOf(gameCreateDTO.getMinAge()));
        return game;
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
