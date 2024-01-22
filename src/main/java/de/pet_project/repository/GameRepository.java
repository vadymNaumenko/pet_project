package de.pet_project.repository;

import de.pet_project.domain.Game;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import de.pet_project.dto.game.GameShortDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface GameRepository extends  JpaRepository<Game, Integer> {
    @Query("SELECT g FROM Game g WHERE g.title LIKE %:title% ")
    Page<Game> findByTitle(String title, Pageable pageable);
}
