package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.models.Transaction;
import org.backend.bankwebapplication.models.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void createTransaction(User sender, User receiver, BigDecimal amount, String currency, String type);

    List<Transaction> findTransactionsByUserId(Long userId);
}
