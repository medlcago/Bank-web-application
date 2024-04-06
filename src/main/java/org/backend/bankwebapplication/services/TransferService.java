package org.backend.bankwebapplication.services;

import java.math.BigDecimal;

public interface TransferService {
    void transferFunds(String senderUsername, String receiverUsername, BigDecimal amount, String currency);
}
