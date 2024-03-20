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
    @Size(min = 5, max = 16, message = "Логин должен содержать от 5 до 16 символов")
    private String username;

    @Size(min = 2, max = 64, message = "Имя должно содержать от 2 до 64 символов")
    private String firstName;

    @Size(min = 2, max = 64, message = "Фамилия должна содержать от 2 до 64 символов")
    private String lastName;

    @Email(message = "Электронная почта должна быть действующей")
    private String email;

    @Size(min = 6, max = 32, message = "Пароль должен содержать от 6 до 32 символов")
    private String password;

    @Size(min = 6, max = 32, message = "Пароль должен содержать от 6 до 32 символов")
    private String confirmPassword;
}
