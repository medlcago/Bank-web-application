package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.responses.UserResponse;
import org.backend.bankwebapplication.dto.responses.AboutMeResponse;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.entities.User;

import java.util.Optional;

public interface UserService {
    void createUser(UserRegistrationForm form, String cardType, String cardName, ECurrency currency);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);

    User updateResetPasswordToken(String token, String email);

    Optional<User> findByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);

    AboutMeResponse toAboutMeResponse(User user);

    UserResponse toUserResponse(User user);
}
