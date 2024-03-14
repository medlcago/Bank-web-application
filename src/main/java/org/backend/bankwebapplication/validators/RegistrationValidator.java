package org.backend.bankwebapplication.validators;

import lombok.extern.slf4j.Slf4j;
import org.backend.bankwebapplication.dto.UserRegistrationForm;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.EmailService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class RegistrationValidator implements Validator {
    private final UserRepository repository;

    private final EmailService emailService;

    public RegistrationValidator(UserRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegistrationForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
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
