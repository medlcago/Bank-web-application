package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.response.DailyCursResponse;

public interface CurrencyService {
    DailyCursResponse getDailyCurs();
}
