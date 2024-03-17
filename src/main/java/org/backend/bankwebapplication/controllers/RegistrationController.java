package org.backend.bankwebapplication.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.UserRegistrationForm;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.validators.RegistrationValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final RegistrationValidator validator;

    public RegistrationController(UserRepository repository, PasswordEncoder passwordEncoder, RegistrationValidator validator) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Principal principal, Model model) {
        if (principal != null) {
            return "redirect:/home";
        }
        model.addAttribute("title", "Регистрация");
        model.addAttribute("UserRegistrationForm", new UserRegistrationForm());
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistrationForm(@Valid @ModelAttribute("UserRegistrationForm") UserRegistrationForm form, BindingResult bindingResult, Model model) {
        model.addAttribute("title", "Регистрация");

        validator.validate(form, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = new User(form.getUsername(), form.getFirstName(), form.getLastName(), form.getEmail(), passwordEncoder.encode(form.getPassword()));
        repository.save(user);
        return "redirect:/login?registrationSuccess";
    }
}
