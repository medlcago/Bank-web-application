package org.backend.bankwebapplication.controllers.web;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DailyCursController {
    private final CurrencyService currencyService;

    @GetMapping(value = "/daily-curs")
    public String getDailyCurs(Model model) {
        model.addAttribute("title", "Курс валют");
        model.addAttribute("valutes", currencyService.getDailyCurs().valutes());
        return "daily-curs";
    }
}
