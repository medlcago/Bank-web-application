package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findBySenderIdOrReceiverId(Long senderId, Long receiverId, Pageable pageable);
}
