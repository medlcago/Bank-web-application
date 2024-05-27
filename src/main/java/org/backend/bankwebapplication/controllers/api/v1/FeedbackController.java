package org.backend.bankwebapplication.controllers.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.FeedbackForm;
import org.backend.bankwebapplication.dto.responses.ErrorListResponse;
import org.backend.bankwebapplication.dto.responses.SuccessResponse;
import org.backend.bankwebapplication.services.impl.EmailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController(value = "ApiFeedbackController")
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {
    private final EmailServiceImpl emailService;

    @PostMapping(value = "/feedback")
    public ResponseEntity<?> feedback(@Valid FeedbackForm form) {
        try {
            log.info(form.toString());
            emailService.sendFeedbackEmail(form.getEmail(), form.getUsername(), form.getMessage());
            return ResponseEntity.ok(new SuccessResponse("Спасибо! Ваше сообщение принято"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorListResponse(Map.of("email", "Произошла ошибка при отправке сообщения. Пожалуйста, попробуйте еще раз")));
        }
    }
}
