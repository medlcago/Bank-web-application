package org.backend.bankwebapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactsController {

    @GetMapping(value = "/contacts")
    public String contactPage(Model model) {
        model.addAttribute("title", "Контакты");
        return "contacts";
    }
}