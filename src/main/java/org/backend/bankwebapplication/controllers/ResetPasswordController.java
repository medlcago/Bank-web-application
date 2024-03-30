package org.backend.bankwebapplication.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ResetPasswordController {
    private final UserServiceImpl userService;

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam(value = "token", required = false) String token, Model model, HttpSession session) {
        if (token == null) {
            return "redirect:/forgot-password";
        }

        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            session.setAttribute("invalidToken", "Срок действия ссылки истек");
            return "redirect:/forgot-password";
        }

        model.addAttribute("token", token);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("title", "Сброс пароля");
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(HttpServletRequest request, HttpSession session) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        if (token == null) {
            return "redirect:/forgot-password";
        }

        User user = userService.getByResetPasswordToken(token);
        if (user == null) {
            session.setAttribute("invalidToken", "Срок действия ссылки истек");
            return "redirect:/forgot-password";
        }

        userService.updatePassword(user, password);
        session.setAttribute("resetPasswordSuccess", "Вы успешно сменили пароль");
        return "redirect:/login";
    }
}
