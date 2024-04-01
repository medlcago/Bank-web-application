package org.backend.bankwebapplication.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.ForgotPasswordForm;
import org.backend.bankwebapplication.dto.response.ErrorResponse;
import org.backend.bankwebapplication.dto.response.SuccessResponse;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.services.impl.EmailServiceImpl;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.backend.bankwebapplication.utils.URLUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordController {
    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;

    @GetMapping(value = "forgot-password")
    public String showForgotPasswordForm(Model model, HttpSession session) {
        model.addAttribute("title", "Восстановление пароля");
        String invalidToken = (String) session.getAttribute("invalidToken");
        if (invalidToken != null) {
            session.removeAttribute("invalidToken");
            model.addAttribute("danger", invalidToken);
        }
        return "forgot-password";
    }

    @PostMapping(value = "forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> processForgotPassword(@Valid ForgotPasswordForm form, HttpServletRequest request) {
        log.info(form.toString());

        String email = form.getEmail();
        String token = UUID.randomUUID().toString();

        try {
            User user = userService.updateResetPasswordToken(token, email);
            String username = user.getUsername();
            String resetPasswordLink = URLUtils.getSiteURL(request) + "/reset-password?token=" + token;
            emailService.sendResetPasswordEmail(email, username, resetPasswordLink);
            return ResponseEntity.ok(new SuccessResponse("Мы отправили ссылку для сброса пароля на вашу электронную почту"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(new ErrorResponse(Map.of("email", ex.getMessage())));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse(Map.of("email", "Произошла ошибка при отправке письма")));
        }
    }
}
