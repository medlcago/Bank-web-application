package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.enums.ECurrency;

import java.math.BigDecimal;

public interface TransferService {
    void transferFunds(String senderUsername, String receiverUsername, BigDecimal amount, ECurrency currency);
}
