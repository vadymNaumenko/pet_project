package de.pet_project.repository.user;

import de.pet_project.domain.User;
import de.pet_project.repository.user.FilterUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>, FilterUserRepository {
    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String username);

    Optional<User> findByEmailLike(String e);
    Optional<User> findByEmail(String email);


}
