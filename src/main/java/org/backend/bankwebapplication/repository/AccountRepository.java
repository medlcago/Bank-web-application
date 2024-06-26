package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserIdOrderById(Long userId);
}
