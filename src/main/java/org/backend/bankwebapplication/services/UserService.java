package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.AboutMeDTO;
import org.backend.bankwebapplication.dto.UserDTO;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.models.User;

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

    AboutMeDTO toAboutMeDTO(User user);

    UserDTO toUserDTO(User user);
}
