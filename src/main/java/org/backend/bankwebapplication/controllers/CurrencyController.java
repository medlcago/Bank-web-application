package org.backend.bankwebapplication.controllers;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping(value = "/currency")
    public String getCurrency(Model model) {
        model.addAttribute("title", "Курс валют");
        model.addAttribute("valutes", currencyService.getDailyCurs().valutes());
        return "currency";
    }
}
