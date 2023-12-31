package de.pet_project.repository;

import de.pet_project.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    boolean existsByImageUrl(String imageUrl);
}
