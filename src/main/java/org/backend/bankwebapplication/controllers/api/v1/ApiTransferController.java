package org.backend.bankwebapplication.controllers.api.v1;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.TransferMoneyForm;
import org.backend.bankwebapplication.dto.response.ErrorListResponse;
import org.backend.bankwebapplication.dto.response.SuccessResponse;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.exceptions.AccountNotFoundException;
import org.backend.bankwebapplication.exceptions.InsufficientFundsException;
import org.backend.bankwebapplication.exceptions.MaxTransferAmountExceededException;
import org.backend.bankwebapplication.exceptions.SelfTransferException;
import org.backend.bankwebapplication.services.impl.TransferServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
@Slf4j
public class ApiTransferController {
    private final TransferServiceImpl transferService;

    @PostMapping(value = "/transfer-funds")
    public ResponseEntity<?> transferFunds(@Valid TransferMoneyForm form, Principal principal) {
        try {
            ECurrency currency = ECurrency.valueOf(form.getCurrency().toUpperCase());
            transferService.transferFunds(principal.getName(), form.getReceiver(), form.getAmount(), currency);
            return ResponseEntity.ok(new SuccessResponse("Вы успешно перевели " + form.getAmount() + " " + form.getCurrency() + " пользователю " + form.getReceiver()));
        } catch (UsernameNotFoundException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorListResponse(Map.of("receiver", ex.getMessage())));
        } catch (SelfTransferException | MaxTransferAmountExceededException | AccountNotFoundException |
                 InsufficientFundsException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.badRequest().body(new ErrorListResponse(Map.of("amount", ex.getMessage())));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorListResponse(Map.of("amount", "Произошла неизвестная ошибка")));
        }
    }
}

