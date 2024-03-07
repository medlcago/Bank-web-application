package org.backend.bankwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {
    @GetMapping(value = "/home")
    public String homePage(Principal principal, Model model) {
        model.addAttribute("title", "Main Page");
        if (principal != null) {
            System.out.println(principal.getName());
        }
        return "home";
    }
}
