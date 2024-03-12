package org.backend.bankwebapplication.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.UserRegistrationForm;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.EmailService;
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
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository repository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

        String username = form.getUsername();
        String password = form.getPassword();
        String confirmPassword = form.getConfirmPassword();
        String email = form.getEmail();
        if (repository.findByUsername(username).isPresent()) {
            bindingResult.rejectValue("username", "error.usernameTaken", "Имя пользователя уже используется");
            log.error("Имя пользователя " + username + " уже используется!");
        }

        if (!password.equals(confirmPassword)) {
            bindingResult.rejectValue("confirmPassword", "error.passwordMismatch", "Пароли не совпадают");
            log.error("Пароли не совпадают!");
        }

        if (repository.findByEmail(email).isPresent()) {
            bindingResult.rejectValue("email", "error.emailTaken", "Данный адрес уже используется");
            log.error("Почта " + email + " уже используется!");
        }

        if (!emailService.cleanEmail(email).isPersonal()){
            bindingResult.rejectValue("email", "error.emailInvalid", "Данная почта запрещена для регистрации");
            log.error("Почта " + email + " запрещена!");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = new User(username, email, passwordEncoder.encode(password));
        repository.save(user);
        return "redirect:/login?registrationSuccess";
    }
}
