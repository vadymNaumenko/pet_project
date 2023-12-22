package de.pet_project.repository;

import de.pet_project.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameRepository extends PagingAndSortingRepository<Game, Integer>, JpaRepository<Game, Integer> {
}
