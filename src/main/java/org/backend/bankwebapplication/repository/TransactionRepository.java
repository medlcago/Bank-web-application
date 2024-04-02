package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderIdOrReceiverId(Long senderId, Long receiverId);
}
