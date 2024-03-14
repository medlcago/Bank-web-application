package org.backend.bankwebapplication.services.impl;

import org.backend.bankwebapplication.dto.rates.DailyCurs;
import org.backend.bankwebapplication.services.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    @Override
    public DailyCurs getDailyCurs() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.cbr-xml-daily.ru/daily_utf8.xml";
        ResponseEntity<DailyCurs> response = restTemplate.getForEntity(url, DailyCurs.class);
        return response.getBody();
    }
}