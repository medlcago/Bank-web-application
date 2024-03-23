package org.backend.bankwebapplication.services.impl;

import org.backend.bankwebapplication.dto.UserRegistrationForm;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.Card;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CardServiceImpl cardService;
    private final AccountServiceImpl accountService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CardServiceImpl cardService, AccountServiceImpl accountService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @Override
    public void createCardAndAccount(User user, String cardType, String cardName, String currency) {
        // Создание карты
        Card card = new Card();
        card.setType(cardType);
        card.setName(cardName);
        card.setCardNumber(cardService.generateCardNumber());

        // Создание счета и связь его с картой и пользователем
        Account account = new Account();
        account.setAccountNumber(accountService.generateAccountNumber());
        account.setCurrency(currency.toUpperCase());
        account.setCard(card);
        account.setUser(user);

        user.setAccounts(Stream.of(account).collect(Collectors.toList()));
        userRepository.save(user);
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User createUser(UserRegistrationForm form) {
        User user = new User();
        user.setUsername(form.getUsername());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        return createUser(user);
    }
}
