package org.backend.bankwebapplication.controllers.api.v1;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.responses.TransactionResponse;
import org.backend.bankwebapplication.dto.responses.TransactionHistoryResponse;
import org.backend.bankwebapplication.entities.Transaction;
import org.backend.bankwebapplication.security.user.UserDetailsImpl;
import org.backend.bankwebapplication.services.impl.TransactionServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "${api.v1.prefix}", produces = "application/json")
@RequiredArgsConstructor
public class ApiTransactionHistoryController {
    private final TransactionServiceImpl transactionService;

    @GetMapping(value = "/transactions")
    public ResponseEntity<?> getTransactionsByUserId(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     @RequestParam(defaultValue = "10") int limit,
                                                     @RequestParam(defaultValue = "0") int offset,
                                                     @RequestParam(defaultValue = "id") String sort,
                                                     @RequestParam(defaultValue = "asc") String order) {
        Long userId = userDetails.getId();

        Page<Transaction> transactions = transactionService.findByUserId(userId, limit, offset, sort, order);

        List<TransactionResponse> transactionResponses = transactionService.toTransactionResponseList(transactions.getContent());
        return ResponseEntity.ok(new TransactionHistoryResponse(
                transactions.getTotalElements(),
                transactionResponses
        ));
    }
}
