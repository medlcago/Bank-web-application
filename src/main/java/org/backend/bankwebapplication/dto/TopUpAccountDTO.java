package org.backend.bankwebapplication.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TopUpAccountDTO {
    private String currency;
    @Min(value = 1, message = "Некорректная сумма пополнения")
    private BigDecimal amount;
}
