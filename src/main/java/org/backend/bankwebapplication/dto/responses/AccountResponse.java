package org.backend.bankwebapplication.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(Long id, String accountNumber, BigDecimal balance, String currency,
                              String balanceWithCurrency,
                              @JsonFormat(pattern = "dd.MM.yyyy, HH:mm:ss") LocalDateTime createdAt, CardResponse card) {

}
