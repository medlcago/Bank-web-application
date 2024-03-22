package org.backend.bankwebapplication.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.backend.bankwebapplication.dto.email.EmailCleanResponse;
import org.backend.bankwebapplication.services.EmailService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailServiceImpl implements EmailService {
    private final Environment environment;
    private final JavaMailSender mailSender;

    public EmailServiceImpl(Environment environment, JavaMailSender mailSender) {
        this.environment = environment;
        this.mailSender = mailSender;
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

    @Override
    public void sendEmail(String to, String subject, String message, boolean isHtml) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");

        helper.setTo(to);
        helper.setSubject(subject);

        if (isHtml) {
            mimeMessage.setContent(message, "text/html; charset=utf-8");
        } else {
            mimeMessage.setText(message, "utf-8");
        }

        mailSender.send(mimeMessage);
    }
}

