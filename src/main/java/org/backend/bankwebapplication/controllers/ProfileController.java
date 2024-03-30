package org.backend.bankwebapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.repository.AccountRepository;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final AccountRepository accountRepository;

    @GetMapping(value = "/profile")
    public String getProfile(Model model, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) principal).getPrincipal();
        List<Account> userAccounts = accountRepository.findAllByUserId(userDetails.getID());
        model.addAttribute("title", "Профиль");
        model.addAttribute("userAccounts", userAccounts);
        return "profile";
    }
}