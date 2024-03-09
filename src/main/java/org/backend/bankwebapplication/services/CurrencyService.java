package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.rates.DailyCurs;

public interface CurrencyService {
    DailyCurs getDailyCurs();
}
