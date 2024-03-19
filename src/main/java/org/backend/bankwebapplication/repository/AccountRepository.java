package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
