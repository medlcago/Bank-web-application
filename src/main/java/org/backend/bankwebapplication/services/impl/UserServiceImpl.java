package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.Card;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CardServiceImpl cardService;
    private final AccountServiceImpl accountService;

    @Override
    @Transactional
    public void createCardAndAccount(User user, String cardType, String cardName, String currency) {
        // Создание карты
        Card card = Card.builder()
                .type(cardType)
                .name(cardName)
                .cardNumber(cardService.generateCardNumber(true, "-"))
                .build();

        // Создание счета и связь его с картой и пользователем
        Account account = Account.builder()
                .accountNumber(accountService.generateAccountNumber())
                .currency(currency.toUpperCase())
                .card(card)
                .user(user)
                .build();

        user.setAccounts(Stream.of(account).collect(Collectors.toList()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User createUser(UserRegistrationForm form) {
        User user = User.builder()
                .username(form.getUsername())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(form.getPassword())
                .build();
        return createUser(user);
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
