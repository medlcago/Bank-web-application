package org.backend.bankwebapplication.dto.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordForm {
    @Email(message = "Введите корректный email")
    @Size(min = 1, max = 128, message = "Введите корректный email")
    @NotNull(message = "Пожалуйста, введите email")
    private String email;
}