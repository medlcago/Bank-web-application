package org.backend.bankwebapplication.config;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.models.Currency;
import org.backend.bankwebapplication.repository.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrenciesDataInitializer implements CommandLineRunner {
    private final CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) throws Exception {
        for (ECurrency eCurrency : ECurrency.values()) {
            if (currencyRepository.findByCode(eCurrency).isEmpty()) {
                Currency currency = Currency.builder()
                        .code(eCurrency)
                        .build();
                currencyRepository.save(currency);
            }
        }
    }
}
