package de.pet_project.repository;

import de.pet_project.domain.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface GameRepository extends  JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g WHERE g.genre =:genre")
    Page<Game> findAllByGenre(@Param("genre") Genre genre, Pageable pageable);

    @Query("SELECT g FROM Game g WHERE g.state =:state")
    Page<Game> findAllByState(@Param("state")State state, Pageable pageable);

    @Query("SELECT g FROM Game g WHERE g.minAge =:minAge")
    Page<Game> findAllByMinAge(@Param("minAge")MinAge minAge, Pageable pageable);

    @Query("SELECT g FROM Game g WHERE g.numberOfPlayers =:numberOfPlayers")
    Page<Game> findAllByNumberOfPlayers(@Param("numberOfPlayers")NumberOfPlayers numberOfPlayers, Pageable pageable);
}
