package de.pet_project.repository;

import de.pet_project.domain.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode,Integer> {
    Optional<ConfirmationCode> findByCode(String cod);
}
