package org.backend.bankwebapplication.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.forms.TransferMoneyForm;
import org.backend.bankwebapplication.services.impl.TransferServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TransferController {
    private final TransferServiceImpl transferService;

    @PostMapping(value = "/transfer-funds", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> transferFunds(@Valid TransferMoneyForm form, Principal principal) {
        try {
            transferService.transferFunds(principal.getName(), form.getRecipient(), form.getAmount(), form.getCurrency());
            return ResponseEntity.ok(Map.of("message", "Вы успешно перевели " + form.getAmount() + " " + form.getCurrency() + " пользователю " + form.getRecipient()));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.badRequest().body(Map.of("errors", Map.of("recipient", ex.getMessage())));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("errors", Map.of("amount", ex.getMessage())));
        }
    }
}
