package org.backend.bankwebapplication.controllers.api.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.TopUpAccountForm;
import org.backend.bankwebapplication.dto.responses.ErrorResponse;
import org.backend.bankwebapplication.dto.responses.SuccessResponse;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.entities.Account;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.impl.AccountServiceImpl;
import org.backend.bankwebapplication.services.impl.CurrencyServiceImpl;
import org.backend.bankwebapplication.services.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;

@RestController(value = "ApiTopUpAccountController")
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class TopUpAccountController {
    private final UserServiceImpl userService;
    private final CurrencyServiceImpl currencyService;
    private final AccountServiceImpl accountService;

    @PostMapping(value = "/top-up-account")
    public ResponseEntity<?> topUpAccount(@Valid TopUpAccountForm topUpAccountForm, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Optional<Currency> currency = currencyService.findByCode(ECurrency.valueOf(topUpAccountForm.getCurrency()));
            if (currency.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            BigDecimal amount = topUpAccountForm.getAmount();
            Long userId = userDetails.getId();

            Optional<User> user = userService.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Account account = accountService.getUserAccountByCurrency(user.get(), currency.get());

            accountService.addBalanceToAccount(account, amount);
            return ResponseEntity.status(200).body(new SuccessResponse("Счет был успешно пополнен"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(400, "Произошла неизвестная ошибка"));
        }
    }
}
