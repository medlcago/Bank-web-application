package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.response.DailyCursResponse;
import org.backend.bankwebapplication.models.Currency;
import org.backend.bankwebapplication.enums.ECurrency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {
    DailyCursResponse getDailyCurs();

    Optional<Currency> findByCode(ECurrency code);

    List<Currency> findAll();
}
