package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
