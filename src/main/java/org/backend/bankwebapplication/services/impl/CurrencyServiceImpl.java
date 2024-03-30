package org.backend.bankwebapplication.services.impl;

import org.backend.bankwebapplication.dto.rates.DailyCursResponse;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Override
    public DailyCursResponse getDailyCurs() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.cbr-xml-daily.ru/daily_utf8.xml";
        ResponseEntity<DailyCursResponse> response = restTemplate.getForEntity(url, DailyCursResponse.class);
        return response.getBody();
    }
}