package de.pet_project.repository;

import de.pet_project.domain.ForgotPassword;
import de.pet_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Integer> {
    Optional<ForgotPassword> findByCode(String code);
}
