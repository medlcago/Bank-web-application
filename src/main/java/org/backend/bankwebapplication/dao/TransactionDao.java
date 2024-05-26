package org.backend.bankwebapplication.dao;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.entities.Transaction;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.enums.TransactionType;
import org.backend.bankwebapplication.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TransactionDao {
    private final TransactionRepository transactionRepository;

    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public void create(User sender, User receiver, BigDecimal amount, Currency currency, TransactionType type) {
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .currency(currency)
                .type(type)
                .build();
        save(transaction);
    }

    public Page<Transaction> findByUserId(Long userId, Pageable pageable) {
        return transactionRepository.findBySenderIdOrReceiverId(userId, userId, pageable);
    }
}
