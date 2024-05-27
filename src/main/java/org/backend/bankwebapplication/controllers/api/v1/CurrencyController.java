package org.backend.bankwebapplication.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value="ApiCurrencyController")
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping(value = "/daily-curs")
    public ResponseEntity<?> getDailyCurs() {
        return ResponseEntity.ok(currencyService.getDailyCurs().valutes());
    }
}
