package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Transaction;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.TransactionRepository;
import org.backend.bankwebapplication.services.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void createTransaction(User sender, User receiver, BigDecimal amount, String currency, String type) {
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .currency(currency)
                .type(type)
                .build();
        transactionRepository.save(transaction);
    }
}
