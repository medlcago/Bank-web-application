package org.backend.bankwebapplication.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.UserRegistrationForm;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.backend.bankwebapplication.validators.RegistrationValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@Slf4j
public class RegistrationController {
    private final UserServiceImpl userService;
    private final RegistrationValidator validator;

    public RegistrationController(UserServiceImpl userService, RegistrationValidator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Principal principal, Model model) {
        if (principal != null) {
            return "redirect:/profile";
        }
        model.addAttribute("title", "Регистрация");
        model.addAttribute("UserRegistrationForm", new UserRegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistrationForm(@Valid @ModelAttribute("UserRegistrationForm") UserRegistrationForm form, BindingResult bindingResult, Model model, HttpSession session) {
        model.addAttribute("title", "Регистрация");

        validator.validate(form, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = userService.createUser(form);
        userService.createCardAndAccount(user, "Debit", "Дебетовая карта", "USD");
        session.setAttribute("registrationSuccess", "Регистрация успешна. Пожалуйста, войдите.");
        return "redirect:/login";
    }
}
