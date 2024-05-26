package org.backend.bankwebapplication.dao;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.repository.CurrencyRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyDao {
    private final CurrencyRepository currencyRepository;

    public Optional<Currency> findByCode(ECurrency code) {
        return currencyRepository.findByCode(code);
    }

    public List<Currency> findAll(){
        return currencyRepository.findAll();
    }
}
