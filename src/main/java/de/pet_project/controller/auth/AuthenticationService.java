package de.pet_project.controller.auth;

import de.pet_project.config.JwtService;
import de.pet_project.domain.ConfirmationCode;
import de.pet_project.domain.User;
import de.pet_project.mail.TemplateMailSender;
import de.pet_project.repository.ConfirmationCodeRepository;
import de.pet_project.repository.UserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final TemplateMailSender templateMailSender;
    private final Configuration freemarkerConfiguration;

    @Value("${app.sender.url}")
    private String baseUrl;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
//                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(User.Role.USER)
                .state(User.State.NOT_CONFIRM)
                .build();
        userRepository.save(user);


// mail sender
        String codValid = UUID.randomUUID().toString();
        ConfirmationCode code = ConfirmationCode.builder()
                .code(codValid)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusDays(6))
                .build();

        String link = baseUrl + codValid; //todo mast be add baseUrl/+codValid
        String html;

        try {
            Template template = freemarkerConfiguration.getTemplate("registration_page.ftlh");
            Map<String, Object> model = new HashMap<>();
            model.put("email", user.getEmail());
            model.put("link", link);
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

        confirmationCodeRepository.save(code);
        templateMailSender.sendMail(user.getUsername(), "Registration", html);

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
