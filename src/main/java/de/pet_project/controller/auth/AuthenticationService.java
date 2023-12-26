package de.pet_project.controller.auth;

import de.pet_project.config.JwtService;
import de.pet_project.domain.User;
import de.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
//    private final ConfirmationCodeRepository confirmationCodeRepository;
//    private final TemplateMailSender templateMailSender;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(de.pet_project.domain.User.Role.USER)
                .state(de.pet_project.domain.User.State.NOT_CONFIRM)
                .build();
        userRepository.save(user);
//// mail sender
//        String codValid = UUID.randomUUID().toString();
//        ConfirmationCode code = ConfirmationCode.builder()
//                .code(codValid)
//                .user(user)
//                .expiredDateTime(LocalDateTime.now().plusMinutes(15))
//                .build();
//
//        confirmationCodeRepository.save(code);
//        templateMailSender.sendMail(user.getUsername(), "Registration","http://localhost:8080/"+ codValid); //todo mast be add baseUrl/+codValid
// end
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
}
