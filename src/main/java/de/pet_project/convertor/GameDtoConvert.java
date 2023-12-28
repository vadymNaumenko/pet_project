package de.pet_project.convertor;

import de.pet_project.domain.Game;
import de.pet_project.domain.Genre;
import de.pet_project.dto.game.GameDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameDtoConvert {

    private final ModelMapper modelMapper;

    public GameDTO convertToGameDTO(Game game){
      GameDTO gameDTO = modelMapper.map(game,GameDTO.class);
      gameDTO.setGenre(game.getGenre().name());
      return gameDTO;
    }

    public Game convertToGame(GameDTO gameDTO){
        Game game = modelMapper.map(gameDTO,Game.class);
        game.setGenre(Genre.valueOf(gameDTO.getGenre()));
        return game;
    }


}
