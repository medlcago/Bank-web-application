package org.backend.bankwebapplication.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.FeedbackForm;
import org.backend.bankwebapplication.services.impl.EmailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    private final EmailServiceImpl emailService;

    @PostMapping(value = "feedback")
    public ResponseEntity<?> feedback(@Valid FeedbackForm form, Principal principal) {
        try {
            log.info(form.toString());
            emailService.sendFeedbackEmail(form.getEmail(), principal.getName(), form.getMessage());
            return ResponseEntity.ok(Map.of("message", "Спасибо! Ваше сообщение принято"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("errors", Map.of("email", "Произошла ошибка при отправке сообщения. Пожалуйста, попробуйте еще раз")));
        }
    }
}
