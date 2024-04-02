package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.models.User;

import java.math.BigDecimal;

public interface TransactionService {
    void createTransaction(User sender, User receiver, BigDecimal amount, String currency, String type);
}
