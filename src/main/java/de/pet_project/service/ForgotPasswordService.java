package de.pet_project.service;

import de.pet_project.domain.ForgotPassword;
import de.pet_project.domain.User;
import de.pet_project.repository.ForgotPasswordRepository;
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
public class ForgotPasswordService {
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String email, String code) {
        log.info("Saving forgot password request for email: {}", email);

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            ForgotPassword forgotPassword = ForgotPassword.builder()
                    .active(true)
                    .user(user)
                    .code(code)
                    .createdAt(LocalDateTime.now())
                    .build();

            forgotPasswordRepository.save(forgotPassword);

            log.info("Forgot password request saved successfully for email: {}", email);
        } else {
            log.warn("User not found for email: {}", email);
        }
    }
}
