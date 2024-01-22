package de.pet_project.repository;

import de.pet_project.domain.LocationPromotion;
import de.pet_project.domain.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface LocationPromotionRepository extends JpaRepository<LocationPromotion, Integer> {

    @Query("SELECT lp.promotion FROM LocationPromotion lp WHERE lp.address.id = :addressId")
    Page<Promotion> findAllByAddress(Pageable pageable, Integer addressId);

    @Query("SELECT lp.promotion FROM LocationPromotion lp WHERE lp.address.city = :city")
    Page<Promotion> findAllByCity(Pageable pageable, String city);
}
