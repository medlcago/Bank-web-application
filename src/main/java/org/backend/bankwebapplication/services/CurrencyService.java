package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.rates.DailyCursResponse;

public interface CurrencyService {
    DailyCursResponse getDailyCurs();
}
