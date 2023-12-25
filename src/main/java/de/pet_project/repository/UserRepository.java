package de.pet_project.repository;

import de.pet_project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String username);

    Optional<User> findByEmail(String email);


}
