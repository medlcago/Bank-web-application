package org.backend.bankwebapplication.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.email.EmailCleanResponse;
import org.backend.bankwebapplication.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${dadata.token}")
    private String dadataToken;

    @Value("${dadata.secret}")
    private String dadataSecret;

    @Value("${dadata.url}")
    private String dadataUrl;

    @Value("${forgot-password.resetPasswordMessage}")
    private String resetPasswordMessage;

    @Value("${spring.mail.username}")
    private String feedbackRecipient;

    private final JavaMailSender mailSender;

    @Override
    public EmailCleanResponse cleanEmail(String email) throws RuntimeException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Token " + dadataToken);
        headers.set("X-Secret", dadataSecret);

        HttpEntity<String[]> requestEntity = new HttpEntity<>(new String[]{email}, headers);
        ResponseEntity<EmailCleanResponse[]> responseEntity = restTemplate.postForEntity(dadataUrl, requestEntity, EmailCleanResponse[].class);
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

    public void sendResetPasswordEmail(String to, String username, String resetPasswordLink) throws MessagingException {
        String subject = "Восстановление пароля | %s".formatted(username);
        String message = String.format(resetPasswordMessage, username, resetPasswordLink);
        sendEmail(to, subject, message, true);
    }

    public void sendFeedbackEmail(String to, String username, String message) throws MessagingException {
        String emailContent = "<h1>Форма обратной связи</h1>"
                + "<p>Сообщение от: " + to + "</p>"
                + "<p>" + message + "</p>";
        sendEmail(feedbackRecipient, "Форма обратной связи | %s".formatted(username), emailContent, true);

        String confirmationContent = "<h1>Форма обратной связи</h1>"
                + "<p>Спасибо, что воспользовались формой обратной связи. Ваше сообщение принято.</p>";
        sendEmail(to, "Форма обратной связи | %s".formatted(username), confirmationContent, true);
    }
}

