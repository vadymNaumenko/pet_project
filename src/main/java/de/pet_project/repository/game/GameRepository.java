package de.pet_project.repository.game;

import de.pet_project.domain.game.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface GameRepository extends  JpaRepository<Game, Integer> {
    @Query("SELECT g FROM Game g WHERE g.title LIKE %:title% ")
    Page<Game> findByTitle(String title, Pageable pageable);
}
