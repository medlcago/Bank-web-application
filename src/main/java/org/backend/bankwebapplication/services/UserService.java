package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.models.User;

public interface UserService {

    void createCardAndAccount(User user, String cardType, String cardName, String currency);

    void createUser(UserRegistrationForm form, String cardType, String cardName, String currency);

    User updateResetPasswordToken(String token, String email);

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);
}
