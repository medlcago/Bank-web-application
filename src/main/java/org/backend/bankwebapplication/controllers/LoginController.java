package org.backend.bankwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LoginController {
    @GetMapping(value = "/login")
    public String loginPage(Principal principal, Model model) {
        if (principal != null){
            return "redirect:/profile";
        }
        model.addAttribute("title", "Вход");
        return "login";
    }
}
