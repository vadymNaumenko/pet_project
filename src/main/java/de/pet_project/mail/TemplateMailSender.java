package de.pet_project.mail;

import de.pet_project.domain.ConfirmationCode;
import de.pet_project.domain.Game;
import de.pet_project.domain.TicketOrder;
import de.pet_project.domain.User;
import de.pet_project.repository.ConfirmationCodeRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TemplateMailSender {

    private final JavaMailSender javaMailSender;
    private final ConfirmationCodeRepository confirmationCodeRepository;
    private final Configuration freemarkerConfiguration;

    @Value("${app.sender.url}")
    private String baseUrl;
    @Value("${app.sender.url}")
    private String buyUrl;

    @Async
    public void sendMail(String email, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text,true);
            helper.setFrom("NewSecurity3+"); //todo delete
        } catch (MessagingException e) {
            throw new IllegalArgumentException(e);
        }
        javaMailSender.send(message);
    }

    public void createConfirmCodeAndSend(User user)  {

        String codValid = UUID.randomUUID().toString();
        ConfirmationCode code = ConfirmationCode.builder()
                .code(codValid)
                .user(user)
                .expiredDateTime(LocalDateTime.now().plusDays(6))
                .build();

        String html;
        try {
            Template template = freemarkerConfiguration.getTemplate("registration_page.ftlh");
            Map<String, Object> model = new HashMap<>();
            model.put("link", baseUrl + codValid);
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }

        confirmationCodeRepository.save(code);
        sendMail(user.getUsername(), "Registration", html);

    }

    public void sendTicket(TicketOrder ticketOrder) {

        String html;
        try {
            Template template = freemarkerConfiguration.getTemplate("game_page.ftlh");
            Map<String, Object> model = new HashMap<>();
            Game game = ticketOrder.getGame();
            model.put("image",game.getImage());
            model.put("title",game.getTitle());
            model.put("description",game.getDescription());
            model.put("price",game.getPrice());
            //todo mast be put number ticket

            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
        sendMail(ticketOrder.getUser().getEmail(), "Game", html);
    }
}
