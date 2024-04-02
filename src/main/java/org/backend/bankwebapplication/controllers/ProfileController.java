package org.backend.bankwebapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.Transaction;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.impl.AccountServiceImpl;
import org.backend.bankwebapplication.services.impl.TransactionServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final AccountServiceImpl accountService;
    private final TransactionServiceImpl transactionService;

    @GetMapping(value = "/profile")
    public String getProfile(Model model, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((Authentication) principal).getPrincipal();
        Long userId = userDetails.getId();

        List<Account> userAccounts = accountService.findByUserId(userId);
        List<Transaction> userTransactions = transactionService.findTransactionsByUserId(userId);

        model.addAttribute("title", "Профиль");
        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("userTransactions", userTransactions);
        return "profile";
    }
}