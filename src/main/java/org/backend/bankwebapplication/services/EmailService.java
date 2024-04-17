package org.backend.bankwebapplication.services;

import jakarta.mail.MessagingException;

public interface EmailService {
    boolean isValidEmail(String email);

    void sendEmail(String to, String subject, String message, boolean isHtml) throws MessagingException;
}
