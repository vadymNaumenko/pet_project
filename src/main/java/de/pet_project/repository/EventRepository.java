package de.pet_project.repository;

import de.pet_project.domain.post.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    boolean existsByImageUrl(String imageUrl);

    @Query("select e from Event e where  e.title like %:title%")
    List<Event> findAllByTitle(String title);
    @Query("select e from Event e where  e.title like %:title% and e.isDeleted = false")
    List<Event> findByTitle(String title);
}
