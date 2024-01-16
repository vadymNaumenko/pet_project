package de.pet_project.repository;

import de.pet_project.domain.post.ReactionToPostCommit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionToPostCommitRepository extends JpaRepository<ReactionToPostCommit,Long>{
}
