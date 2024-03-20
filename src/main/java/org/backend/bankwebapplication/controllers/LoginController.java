package org.backend.bankwebapplication.controllers;

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

        String authErrorMessage = (String) session.getAttribute("authErrorMessage");
        if (authErrorMessage != null) {
            session.removeAttribute("authErrorMessage");
            model.addAttribute("authErrorMessage", authErrorMessage);
        }

        model.addAttribute("title", "Вход");
        return "login";
    }
}
