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
    @Size(min = 5, max = 128, message = "Email должен быть от 5 до 128 символов")
    @NotNull(message = "Пожалуйста, введите email")
    private String email;
}