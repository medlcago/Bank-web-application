package org.backend.bankwebapplication.dto;

import java.math.BigDecimal;

public record TransactionDTO(Long id, String senderUsername, String receiverUsername, BigDecimal amount, String currency, String type,
                             String createdAt) {

}
