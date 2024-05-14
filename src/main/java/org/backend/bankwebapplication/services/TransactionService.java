package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.responses.TransactionResponse;
import org.backend.bankwebapplication.enums.TransactionType;
import org.backend.bankwebapplication.entities.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void createTransaction(User sender, User receiver, BigDecimal amount, Currency currency, TransactionType type);

    Page<Transaction> findByUserId(Long userId, int limit, int offset, String sort, String order);

    List<TransactionResponse> toTransactionResponseList(List<Transaction> transactions);
}
