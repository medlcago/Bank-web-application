package org.backend.bankwebapplication.dto.forms;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyForm {
    @Size(min = 5, max = 16, message = "Имя получателя должно содержать от 5 до 16 символов")
    @NotNull(message = "Имя получателя не может быть пустым")
    private String receiver;

    @Min(value = 1, message = "Некорректная сумма")
    @NotNull(message = "Сумма не может быть пустой")
    private BigDecimal amount;

    @NotNull(message = "Валюта не может быть пустой")
    private String currency;
}
