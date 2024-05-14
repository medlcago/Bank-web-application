package org.backend.bankwebapplication.dto.responses;

import java.math.BigDecimal;

public record TransactionResponse(Long id, String senderUsername, String receiverUsername, BigDecimal amount,
                                  String currency, String amountWithCurrency, String type,
                                  String createdAt) {

}
