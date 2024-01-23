package de.pet_project.repository.news_and_comment;

import de.pet_project.domain.news.CommentOnNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentOnNewsRepository extends JpaRepository<CommentOnNews,Long> {
}
