package org.backend.bankwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping(value = "/home")
    public String homePage(Model model, Principal principal) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
}
