package de.pet_project.repository;

import de.pet_project.domain.Game;
import de.pet_project.domain.LocationGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LocationGameRepository extends JpaRepository<LocationGame, Integer> {
    @Query("SELECT lg.game FROM LocationGame lg WHERE lg.address.id = :addressId")
    Page<Game> findAllByAddress(Pageable pageable, Integer addressId);

    @Query("SELECT lg.game FROM LocationGame lg WHERE lg.address.city = :city")
    Page<Game> findAllByCity(Pageable pageable, String city);
}
