package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.email.EmailCleanResponse;

public interface EmailService {
    EmailCleanResponse cleanEmail(String email);
}
