package de.pet_project.repository.news_and_comment;

import de.pet_project.domain.news.CommentOnNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentOnNewsRepository extends JpaRepository<CommentOnNews,Long> {

    Page<CommentOnNews> findByNews_Id(Long newsId, Pageable pageable);
    List<CommentOnNews> findByNews_Id(Long newsId);
}
