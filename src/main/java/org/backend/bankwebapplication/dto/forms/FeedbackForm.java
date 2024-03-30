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
public class FeedbackForm {
    @Size(min = 5, max = 16, message = "username должен быть от 5 до 16 символов")
    @NotNull(message  = "Пожалуйста, введите username")
    private String username;

    @Email(message = "Введите корректный email")
    @Size(min = 5, max = 128, message = "Email должен быть от 5 до 128 символов")
    @NotNull(message  = "Пожалуйста, введите email")
    private String email;

    @Size(min = 20, max = 1024, message = "Сообщение должно быть от 20 до 1024 символов")
    @NotNull(message = "Пожалуйста, введите сообщение")
    private String message;
}