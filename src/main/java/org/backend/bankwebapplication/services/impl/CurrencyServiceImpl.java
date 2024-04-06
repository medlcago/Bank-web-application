package org.backend.bankwebapplication.services.impl;

import org.backend.bankwebapplication.dto.response.DailyCursResponse;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Value("${cbr.url}")
    private String url;

    @Override
    public DailyCursResponse getDailyCurs() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DailyCursResponse> response = restTemplate.getForEntity(url, DailyCursResponse.class);
        return response.getBody();
    }
}