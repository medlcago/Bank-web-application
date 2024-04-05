package org.backend.bankwebapplication.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/", produces = "application/json")
@RequiredArgsConstructor
public class ApiCurrencyController {
    private final CurrencyService currencyService;

    @GetMapping(value = "/daily-curs")
    public ResponseEntity<?> getDailyCurs() {
        return ResponseEntity.ok(currencyService.getDailyCurs().valutes());
    }
}
