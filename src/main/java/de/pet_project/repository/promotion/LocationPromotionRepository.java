package de.pet_project.repository.promotion;

import de.pet_project.domain.enums.State;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.game.Game;
import de.pet_project.domain.promotion.LocationPromotion;
import de.pet_project.domain.promotion.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LocationPromotionRepository extends JpaRepository<LocationPromotion, Integer> {

    @Query("SELECT lp.promotion FROM LocationPromotion lp WHERE " +
            "(:addressId is null or lp.address.id = :addressId) and " +
            "(:city is null or lp.address.city = :city) and " +
            "(:state is null or lp.promotion.state = :state)")
    Page<Promotion> findAllByFilter(@Param("addressId") Integer addressId, @Param("city") String city,
                               @Param("state") State state, Pageable pageable);
}
