package de.pet_project.controller.auth;

import de.pet_project.config.JwtService;
import de.pet_project.domain.ForgotPassword;
import de.pet_project.domain.User;
import de.pet_project.mail.TemplateMailSender;
import de.pet_project.repository.ForgotPasswordRepository;
import de.pet_project.repository.user.UserRepository;
import de.pet_project.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TemplateMailSender templateMailSender;
    private final ForgotPasswordRepository forgotPasswordRepository;


    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
//                .nickname(request.getNickname())
//                .lastname(request.getLastName())
//                .firstname(request.getFirstName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(User.Role.USER)
                .state(User.State.NOT_CONFIRM)
                .avatar("img.png")
                .build();
        userRepository.save(user);

        templateMailSender.createConfirmCodeAndSend(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean forgotPassword(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return false;
        }
        templateMailSender.forgotPassword(email);
       return true;
    }

    @Transactional
    public boolean setNewPassword(ForgotRequest forgotRequest) {

        Optional<ForgotPassword> forgotPassword = forgotPasswordRepository.findByCode(forgotRequest.getCode());
        if (forgotPassword.isPresent() && forgotPassword.get().isActive()) {
            if (LocalDateTime.now().isAfter(forgotPassword.get().getCreatedAt().plusDays(1))) {
                //todo check
                System.out.println();
            }
            User user = forgotPassword.get().getUser();
            user.setPassword(passwordEncoder.encode(forgotRequest.getPassword()));
            forgotPassword.get().setActive(false);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
