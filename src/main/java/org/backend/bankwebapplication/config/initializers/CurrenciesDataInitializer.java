package org.backend.bankwebapplication.config.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.models.Currency;
import org.backend.bankwebapplication.repository.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrenciesDataInitializer implements CommandLineRunner {
    private final CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing currencies...");
        List<Currency> currencies = new ArrayList<>();
        for (ECurrency eCurrency : ECurrency.values()) {
            if (currencyRepository.findByCode(eCurrency).isEmpty()) {
                Currency currency = Currency.builder()
                        .code(eCurrency)
                        .build();
                currencies.add(currency);
            }
        }
        if (!currencies.isEmpty()) {
            currencyRepository.saveAll(currencies);
        }
    }
}
