package org.backend.bankwebapplication.controllers;

import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrencyController {
    CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "/currency")
    public String getCurrency(Model model) {
        model.addAttribute("title", "Курс валют");
        model.addAttribute("valutes", currencyService.getDailyCurs().valutes());
        return "currency";
    }
}
