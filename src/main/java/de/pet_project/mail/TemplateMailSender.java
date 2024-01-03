package de.pet_project.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemplateMailSender {
    private final JavaMailSender javaMailSender;

    @Async
    public void sendMail(String email, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

        String str ;
        try {
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text,true);
            helper.setFrom("NewSecurity3+"); // delete
        } catch (MessagingException e) {
            throw new IllegalArgumentException(e);
        }
        javaMailSender.send(message);
    }
}
