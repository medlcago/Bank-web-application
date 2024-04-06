package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.TransactionDTO;
import org.backend.bankwebapplication.models.Transaction;
import org.backend.bankwebapplication.models.User;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    void createTransaction(User sender, User receiver, BigDecimal amount, String currency, String type);

    Page<Transaction> findByUserId(Long userId, int limit, int offset, String sort, String order);

    List<TransactionDTO> toDTOList(List<Transaction> transactions);
}
