package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Currency;
import org.backend.bankwebapplication.enums.ECurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByName(ECurrency name);
}
