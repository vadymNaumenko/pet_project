package de.pet_project.repository;

import de.pet_project.domain.post.CommitForPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommitForPostRepository extends JpaRepository<CommitForPost,Long> {
}
