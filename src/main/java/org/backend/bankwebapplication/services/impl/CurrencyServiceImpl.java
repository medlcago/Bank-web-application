package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.response.DailyCursResponse;
import org.backend.bankwebapplication.models.Currency;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.repository.CurrencyRepository;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    @Value("${cbr.url}")
    private String url;

    @Override
    public DailyCursResponse getDailyCurs() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DailyCursResponse> response = restTemplate.getForEntity(url, DailyCursResponse.class);
        return response.getBody();
    }

    @Override
    public Optional<Currency> findByName(ECurrency name) {
        return currencyRepository.findByName(name);
    }
}