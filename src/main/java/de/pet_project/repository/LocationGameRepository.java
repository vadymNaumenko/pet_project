package de.pet_project.repository;

import de.pet_project.domain.Game;
import de.pet_project.domain.LocationGame;
import de.pet_project.domain.enums.game.Genre;
import de.pet_project.domain.enums.game.MinAge;
import de.pet_project.domain.enums.game.NumberOfPlayers;
import de.pet_project.domain.enums.game.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocationGameRepository extends JpaRepository<LocationGame, Integer> {
    @Query("SELECT lg.game FROM LocationGame lg WHERE " +
            "(:addressId is null or lg.address.id = :addressId) and " +
            "(:city is null or lg.address.city = :city) and " +
            "(:genre is null or lg.game.genre = :genre) and " +
            "(:state is null or lg.game.state = :state) and " +
            "(:numberOfPlayers is null or lg.game.numberOfPlayers = :numberOfPlayers) and " +
            "(:minAge is null or lg.game.minAge = :minAge)")
    Page<Game> findAllByFilter(@Param("addressId") Integer addressId, @Param("city") String city,
                               @Param("genre") Genre genre, @Param("state") State state,
                               @Param("numberOfPlayers") NumberOfPlayers numberOfPlayers,
                               @Param("minAge") MinAge minAge, Pageable pageable);
}
