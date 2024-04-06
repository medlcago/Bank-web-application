package org.backend.bankwebapplication.controllers.html;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.impl.AccountServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final AccountServiceImpl accountService;

    @GetMapping(value = "/profile")
    public String getProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();

        List<Account> userAccounts = accountService.findByUserId(userId);

        model.addAttribute("title", "Профиль");
        model.addAttribute("userAccounts", userAccounts);
        return "profile";
    }
}