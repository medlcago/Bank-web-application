package org.backend.bankwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    // TODO
    @GetMapping(value = "/profile")
    public String getProfile(Model model) {
        model.addAttribute("title", "Профиль");
        return "profile";
    }
}