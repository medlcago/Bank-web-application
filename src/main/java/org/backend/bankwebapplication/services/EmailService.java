package org.backend.bankwebapplication.services;

import jakarta.mail.MessagingException;
import org.backend.bankwebapplication.dto.response.EmailCleanResponse;

public interface EmailService {
    EmailCleanResponse cleanEmail(String email);

    void sendEmail(String to, String subject, String message, boolean isHtml) throws MessagingException;
}
