package de.pet_project.repository;

import de.pet_project.domain.post.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    boolean existsByImageUrl(String imageUrl);

    @Query("select e from News e where  e.title like %:title%")
    List<News> findAllByTitle(String title);
    @Query("select e from News e where  e.title like %:title% and e.isDeleted = false")
    List<News> findByTitle(String title);
}
