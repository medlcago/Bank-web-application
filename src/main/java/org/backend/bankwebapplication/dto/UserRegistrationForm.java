package org.backend.bankwebapplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationForm {
    @Size(min = 6, max = 16, message = "Имя должно содержать от 6 до 16 символов")
    private String username;

    @Email(message = "Электронная почта должна быть действующей")
    private String email;

    @Size(min = 6, max = 32, message = "Пароль должен содержать от 6 до 32 символов")
    private String password;

    @Size(min = 6, max = 32, message = "Пароль должен содержать от 6 до 32 символов")
    private String confirmPassword;
}
