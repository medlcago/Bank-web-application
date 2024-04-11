package org.backend.bankwebapplication.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.CreateAccountDTO;
import org.backend.bankwebapplication.dto.response.ErrorResponse;
import org.backend.bankwebapplication.dto.response.SuccessResponse;
import org.backend.bankwebapplication.enums.CardType;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.models.Currency;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.CurrencyService;
import org.backend.bankwebapplication.services.UserService;
import org.backend.bankwebapplication.services.impl.AccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class ApiCreateAccountController {
    private final UserService userService;
    private final CurrencyService currencyService;
    private final AccountServiceImpl accountService;

    @PostMapping(value = "/create-account")
    public ResponseEntity<?> createAccount(CreateAccountDTO createAccountDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            CardType cardType = createAccountDTO.cardType();
            ECurrency currencyType = createAccountDTO.currencyType();

            Optional<User> user = userService.findById(userId);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Optional<Currency> currency = currencyService.findByCode(currencyType);
            if (currency.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            accountService.createCardAndAccount(user.get(), cardType.name(), cardType.getDescription(), currency.get());
            return ResponseEntity.status(201).body(new SuccessResponse("Счет был успешно создан"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(400, "Произошла неизвестная ошибка"));
        }
    }
}
