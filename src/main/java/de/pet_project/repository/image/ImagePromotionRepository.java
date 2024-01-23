package de.pet_project.repository.image;

import de.pet_project.domain.image.Image;
import de.pet_project.domain.image.ImagePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagePromotionRepository extends JpaRepository<ImagePromotion, Integer> {
    @Query("SELECT ip.promotion FROM ImagePromotion ip WHERE ip.promotion.id = :promotionId")
    List<Image> findAllByPromotionId(@Param("gameId") Integer promotionId);
}
