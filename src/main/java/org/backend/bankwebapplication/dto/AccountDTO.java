package org.backend.bankwebapplication.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountDTO(Long id, String accountNumber, BigDecimal balance, String currency, LocalDateTime createdAt, CardDTO card) {

}
