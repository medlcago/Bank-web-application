package org.backend.bankwebapplication.services;

import java.math.BigDecimal;

public interface TransferService {
    void transferFunds(String senderUsername, String recipientUsername, BigDecimal amount, String currency);
}
