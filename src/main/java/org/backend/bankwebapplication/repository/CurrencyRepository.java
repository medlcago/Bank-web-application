package org.backend.bankwebapplication.repository;

import lombok.NonNull;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(ECurrency code);

    @NonNull
    List<Currency> findAll();
}
