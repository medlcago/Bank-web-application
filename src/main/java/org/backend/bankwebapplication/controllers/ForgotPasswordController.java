package org.backend.bankwebapplication.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.services.impl.EmailServiceImpl;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.backend.bankwebapplication.utils.URLUtils;
import org.springframework.beans.factory.annotation.Value;
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
public class ForgotPasswordController {
    @Value("${forgot-password.resetPasswordMessage}")
    private String resetPasswordMessage;

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
    public ResponseEntity<?> processForgotPassword(HttpServletRequest request) {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();

        try {
            User user = userService.updateResetPasswordToken(token, email);
            String username = user.getUsername();
            String resetPasswordLink = URLUtils.getSiteURL(request) + "/reset-password?token=" + token;
            String resetPasswordMessage = getResetPasswordMessage(resetPasswordLink, username);
            emailService.sendEmail(email, "Восстановление пароля | " + username, resetPasswordMessage, true);
            return ResponseEntity.ok(Map.of("message", "Мы отправили ссылку для сброса пароля на вашу электронную почту"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        } catch (MessagingException ex) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Произошла ошибка при отправке письма"));
        }
    }

    private String getResetPasswordMessage(String resetPasswordLink, String username) {
        return String.format(resetPasswordMessage, username, resetPasswordLink);
    }
}
