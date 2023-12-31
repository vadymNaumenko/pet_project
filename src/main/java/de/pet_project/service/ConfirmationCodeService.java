package de.pet_project.service;

import de.pet_project.domain.ConfirmationCode;
import de.pet_project.domain.User;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {

    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final UserRepository userRepository;

    public ConfirmationCode findByCode(String code) {
        return confirmationCodeRepository.findByCode(code).orElseThrow();
    }

    public boolean isValid(String code) {
        return confirmationCodeRepository.findByCode(code).isPresent();
    }

    @Transactional
    public boolean setState(String code) {

        if (isValid(code)) {
            User user = userRepository.findByEmail(findByCode(code).getUser().getEmail())
                    .orElseThrow();

            user.setState(User.State.CONFIRMED);
            userRepository.save(user);

            return true;
        }
        return false;
    }
}
