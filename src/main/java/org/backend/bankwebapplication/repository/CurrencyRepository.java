package org.backend.bankwebapplication.repository;

import org.backend.bankwebapplication.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
