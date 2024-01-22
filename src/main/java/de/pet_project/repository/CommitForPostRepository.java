package de.pet_project.repository;

import de.pet_project.domain.post.CommentOnNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitForPostRepository extends JpaRepository<CommentOnNews,Long> {
}
