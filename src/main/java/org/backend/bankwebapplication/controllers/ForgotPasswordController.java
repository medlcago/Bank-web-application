package org.backend.bankwebapplication.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.services.impl.EmailServiceImpl;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
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
public class ForgotPasswordController {
    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;

    public ForgotPasswordController(UserServiceImpl userService, EmailServiceImpl emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

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
            String resetPasswordLink = getSiteURL(request) + "/reset-password?token=" + token;
            String resetPasswordMessage = getResetPasswordMessage(resetPasswordLink, username);
            emailService.sendEmail(email, "Восстановление пароля | " + username, resetPasswordMessage, true);
            return ResponseEntity.ok(Map.of("message", "Мы отправили ссылку для сброса пароля на вашу электронную почту"));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        } catch (MessagingException ex) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Произошла ошибка при отправке письма"));
        }
    }

    private static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    private static String getResetPasswordMessage(String resetPasswordLink, String username) {
        return "<p>Привет, " + username + "!</p>"
                + "<p>Вы запросили сброс пароля.</p>"
                + "<p>Перейдите по ссылке ниже, чтобы изменить свой пароль:</p>"
                + "<p><a href=\"" + resetPasswordLink + "\">Изменить мой пароль</a></p>"
                + "<br>"
                + "<p>Пожалуйста, проигнорируйте это письмо, если вы его не запрашивали</p>";
    }
}
