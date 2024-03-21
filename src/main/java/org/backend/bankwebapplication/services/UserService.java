package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.UserRegistrationForm;
import org.backend.bankwebapplication.models.User;

public interface UserService {

    void createCardAndAccount(User user, String cardType, String cardName, String currency);

    User createUser(User user);

    User createUser(UserRegistrationForm form);
}
