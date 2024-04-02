package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountServiceImpl accountService;

    @Override
    @Transactional
    public void createUser(UserRegistrationForm form, String cardType, String cardName, String currency) {
        User user = User.builder()
                .username(form.getUsername())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
        accountService.createCardAndAccount(user, cardType, cardName, currency);
    }

    @Override
    @Transactional
    public User updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Почта " + email + " не используется на сайте"));
        user.setResetPasswordToken(token);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
