package org.backend.bankwebapplication.controllers;

import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.repository.AccountRepository;
import org.backend.bankwebapplication.services.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ProfileController {
    private final AccountRepository accountRepository;

    public ProfileController(AccountRepository userRepository) {
        this.accountRepository = userRepository;
    }

    @GetMapping(value = "/profile")
    public String getProfile(Model model, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) principal).getPrincipal();
        List<Account> userAccounts = accountRepository.findAllByUserId(userDetails.getID());
        model.addAttribute("title", "Профиль");
        model.addAttribute("userAccounts", userAccounts);
        return "profile";
    }
}