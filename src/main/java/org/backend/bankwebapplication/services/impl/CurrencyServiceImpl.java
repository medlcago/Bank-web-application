package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dao.CurrencyDao;
import org.backend.bankwebapplication.dto.responses.DailyCursResponse;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyDao currencyDao;

    @Value("${cbr.url}")
    private String url;

    @Override
    public DailyCursResponse getDailyCurs() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DailyCursResponse> response = restTemplate.getForEntity(url, DailyCursResponse.class);
        return response.getBody();
    }

    @Override
    public Optional<Currency> findByCode(ECurrency code) {
        return currencyDao.findByCode(code);
    }

    @Override
    public List<Currency> findAll() {
        return currencyDao.findAll();
    }
}