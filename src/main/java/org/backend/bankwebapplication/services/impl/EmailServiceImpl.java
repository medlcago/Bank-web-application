package org.backend.bankwebapplication.services.impl;

import org.backend.bankwebapplication.dto.email.EmailCleanResponse;
import org.backend.bankwebapplication.services.EmailService;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailServiceImpl implements EmailService {
    private final Environment environment;

    public EmailServiceImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public EmailCleanResponse cleanEmail(String email) throws RuntimeException {
        String url = "https://cleaner.dadata.ru/api/v1/clean/email";
        String token = environment.getRequiredProperty("DADATA_TOKEN");
        String secret = environment.getRequiredProperty("DADATA_SECRET");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + token);
        headers.set("X-Secret", secret);

        HttpEntity<String[]> requestEntity = new HttpEntity<>(new String[]{email}, headers);
        ResponseEntity<EmailCleanResponse[]> responseEntity = restTemplate.postForEntity(url, requestEntity, EmailCleanResponse[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            EmailCleanResponse[] response = responseEntity.getBody();
            if (response != null && response.length > 0) {
                return response[0];
            }
        }
        return null;
    }
}
