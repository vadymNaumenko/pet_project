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

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(String email, String code) {
        User user = userRepository.findByEmail(email).get();
        ForgotPassword forgotPassword
                = ForgotPassword.builder()
                .active(true)
                .user(user)
                .code(code)
                .createdAt(LocalDateTime.now()).build();
        forgotPasswordRepository.save(forgotPassword);
    }
}
