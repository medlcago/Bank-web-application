package org.backend.bankwebapplication.validators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationValidator implements Validator {
    private final UserRepository repository;

    private final EmailService emailService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return UserRegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        UserRegistrationForm formToValidate = (UserRegistrationForm) target;

        String username = formToValidate.getUsername();
        String password = formToValidate.getPassword();
        String confirmPassword = formToValidate.getConfirmPassword();
        String email = formToValidate.getEmail();

        if (repository.findByUsername(username).isPresent()) {
            errors.rejectValue("username", "error.usernameTaken", "Имя пользователя уже используется");
            log.error("Имя пользователя " + username + " уже используется!");
        }

        if (!password.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "error.passwordMismatch", "Пароли не совпадают");
            log.error("Пароли не совпадают!");
        }

        if (repository.findByEmail(email).isPresent()) {
            errors.rejectValue("email", "error.emailTaken", "Данный адрес уже используется");
            log.error("Почта " + email + " уже используется!");
        }

        if (!emailService.cleanEmail(email).isPersonal()) {
            errors.rejectValue("email", "error.emailInvalid", "Данная почта запрещена для регистрации");
            log.error("Почта " + email + " запрещена!");
        }

    }
}
