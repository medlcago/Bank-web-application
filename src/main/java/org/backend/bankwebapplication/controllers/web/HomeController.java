package org.backend.bankwebapplication.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value = "/home")
    public String homePage(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home";
    }
}