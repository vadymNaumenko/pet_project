package de.pet_project.repository.image;

import de.pet_project.domain.image.Image;
import de.pet_project.domain.image.ImageGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageGameRepository extends JpaRepository<ImageGame, Integer> {
    @Query("SELECT ig.game FROM ImageGame ig WHERE ig.game.id = :gameId")
    List<Image> findAllByGameId(@Param("gameId") Integer gameId);
}
