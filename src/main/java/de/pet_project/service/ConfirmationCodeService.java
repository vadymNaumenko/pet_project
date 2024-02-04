package de.pet_project.service;

import de.pet_project.domain.ConfirmationCode;
import de.pet_project.domain.User;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserRepository userRepository;

    public Optional<ConfirmationCode> findByCode(String code) {
        log.info("Searching for confirmation code with code: {}", code);
        return confirmationCodeRepository.findByCode(code);
    }

    public boolean isValid(String code) {
        log.info("Checking if confirmation code is valid: {}", code);
        return confirmationCodeRepository.findByCode(code).isPresent();
    }

    @Transactional
    public boolean setState(String code) {
        log.info("Setting state for confirmation code: {}", code);

        if (isValid(code)) {
            String email = findByCode(code).get().getUser().getEmail();
            User user = userRepository.findByEmail(email)
                    .orElseThrow();

            user.setState(User.State.CONFIRMED);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);

            log.info("User state set to CONFIRMED for email: {}", email);
            return true;
        }
        log.warn("Invalid confirmation code: {}", code);
        return false;
    }
}
