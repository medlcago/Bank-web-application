package org.backend.bankwebapplication.controllers.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.exceptions.CurrencyNotFoundException;
import org.backend.bankwebapplication.exceptions.RoleNotFoundException;
import org.backend.bankwebapplication.enums.CardType;
import org.backend.bankwebapplication.enums.ECurrency;
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
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    private final UserServiceImpl userService;
    private final RegistrationValidator validator;

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

        try {
            userService.createUser(form, CardType.DEBIT.name(), CardType.DEBIT.getDescription(), ECurrency.USD);
            session.setAttribute("registrationSuccess", "Регистрация успешна. Пожалуйста, войдите.");
            return "redirect:/login";
        } catch (RoleNotFoundException | CurrencyNotFoundException ex) {
            model.addAttribute("registrationError", ex.getMessage());
            return "registration";
        } catch (Exception ex) {
            log.error("Registration error: {}", ex.getMessage());
            model.addAttribute("registrationError", "Произошла ошибка при регистрации. Пожалуйста, обратитесь к администратору.");
            return "registration";
        }

    }
}
