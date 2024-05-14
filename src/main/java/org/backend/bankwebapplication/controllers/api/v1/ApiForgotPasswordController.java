package org.backend.bankwebapplication.controllers.api.v1;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.ForgotPasswordForm;
import org.backend.bankwebapplication.dto.responses.ErrorListResponse;
import org.backend.bankwebapplication.dto.responses.SuccessResponse;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.services.impl.EmailServiceImpl;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.backend.bankwebapplication.utils.WebUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class ApiForgotPasswordController {
    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;
    private final WebUtils webUtils;

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> processForgotPassword(@Valid ForgotPasswordForm form, HttpServletRequest request) {
        log.info(form.toString());

        String email = form.getEmail();
        String token = UUID.randomUUID().toString();

        try {
            User user = userService.updateResetPasswordToken(token, email);
            String username = user.getUsername();
            String resetPasswordLink = webUtils.getSiteURL(request) + "/reset-password?token=" + token;
            emailService.sendResetPasswordEmail(email, username, resetPasswordLink);
            return ResponseEntity.ok(new SuccessResponse("Мы отправили ссылку для сброса пароля на вашу электронную почту"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(new ErrorListResponse(Map.of("email", ex.getMessage())));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorListResponse(Map.of("email", "Произошла ошибка при отправке письма")));
        }
    }
}
