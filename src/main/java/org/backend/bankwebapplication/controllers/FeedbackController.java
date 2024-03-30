package org.backend.bankwebapplication.controllers;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.FeedbackForm;
import org.backend.bankwebapplication.services.impl.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    @Value("${spring.mail.username}")
    private String recipient;

    private final EmailServiceImpl emailService;

    @PostMapping(value = "feedback")
    public ResponseEntity<?> feedback(@Valid FeedbackForm form) {
        try {
            log.info(form.toString());

            String emailContent = "<h1>Форма обратной связи</h1>"
                    + "<p>Сообщение от: " + form.getEmail() + "</p>"
                    + "<p>" + form.getMessage() + "</p>";
            emailService.sendEmail(recipient, "Форма обратной связи | " + form.getEmail(), emailContent, true);

            String confirmationContent = "<h1>Форма обратной связи</h1>"
                    + "<p>Спасибо, что воспользовались формой обратной связи. Ваше сообщение принято.</p>";
            emailService.sendEmail(form.getEmail(), "Форма обратной связи", confirmationContent, true);

            return ResponseEntity.ok(Map.of("message", "Спасибо! Ваше сообщение принято"));
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("errors", Map.of("email", "Произошла ошибка при отправке сообщения. Пожалуйста, попробуйте еще раз")));
        }
    }
}
