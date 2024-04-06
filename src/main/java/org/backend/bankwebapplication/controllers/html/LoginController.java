package org.backend.bankwebapplication.controllers.html;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {
    @GetMapping(value = "/login")
    public String loginPage(Principal principal, Model model, HttpSession session) {
        if (principal != null) {
            return "redirect:/profile";
        }

        String registrationSuccess = (String) session.getAttribute("registrationSuccess");
        String authErrorMessage = (String) session.getAttribute("authErrorMessage");
        String resetPasswordSuccess = (String) session.getAttribute("resetPasswordSuccess");

        if (authErrorMessage != null) {
            session.removeAttribute("authErrorMessage");
            model.addAttribute("authErrorMessage", authErrorMessage);
        }

        if (resetPasswordSuccess != null) {
            session.removeAttribute("resetPasswordSuccess");
            model.addAttribute("resetPasswordSuccess", resetPasswordSuccess);
        }

        if (registrationSuccess != null) {
            session.removeAttribute("registrationSuccess");
            model.addAttribute("registrationSuccess", registrationSuccess);
        }

        model.addAttribute("title", "Вход");
        return "login";
    }
}
