package org.backend.bankwebapplication.controllers.web;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.enums.CardType;
import org.backend.bankwebapplication.entities.Account;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.impl.AccountServiceImpl;
import org.backend.bankwebapplication.services.impl.CurrencyServiceImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final AccountServiceImpl accountService;
    private final CurrencyServiceImpl currencyService;

    @GetMapping(value = "/profile")
    public String getProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getId();

        List<Account> userAccounts = accountService.findByUserId(userId);

        // Получаем все валюты пользователя
        List<Currency> userCurrencies = userAccounts.stream().map(Account::getCurrency).toList();
        // Получаем все доступные валюты
        List<Currency> currencies = currencyService.findAll();
        // Оставляем только те валюты, которых нет у пользователя
        List<Currency> missingCurrencies = new ArrayList<>(currencies);
        missingCurrencies.removeAll(userCurrencies);

        // Получаем все доступные типы карт
        CardType[] cardTypes = CardType.values();

        model.addAttribute("title", "Профиль");
        model.addAttribute("userAccounts", userAccounts);
        model.addAttribute("missingCurrencies", missingCurrencies);
        model.addAttribute("cardTypes", cardTypes);
        return "profile";
    }
}