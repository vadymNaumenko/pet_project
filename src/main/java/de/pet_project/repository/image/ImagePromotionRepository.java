package de.pet_project.repository.image;

import de.pet_project.domain.image.Image;
import de.pet_project.domain.image.ImagePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImagePromotionRepository extends JpaRepository<ImagePromotion, Integer> {
    @Query("SELECT ip.image FROM ImagePromotion ip WHERE ip.promotion.id = :promotionId")
    List<Image> findAllByPromotionId(@Param("promotionId") Integer promotionId);

    @Query("SELECT ip.image FROM ImagePromotion ip WHERE (:promotionId is null or ip.promotion.id = :promotionId) and " +
            "(:isMain is null or ip.isMain)")
    Optional<Image> findImageByFilter(@Param("promotionId") Integer promotionId, @Param("isMain") boolean isMain);
}
