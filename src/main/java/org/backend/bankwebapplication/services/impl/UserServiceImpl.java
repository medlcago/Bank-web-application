package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dao.UserDao;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.dto.responses.AboutMeResponse;
import org.backend.bankwebapplication.dto.responses.UserResponse;
import org.backend.bankwebapplication.entities.Card;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.entities.Role;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.exceptions.CurrencyNotFoundException;
import org.backend.bankwebapplication.exceptions.RoleNotFoundException;
import org.backend.bankwebapplication.mappers.UserMapper;
import org.backend.bankwebapplication.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final AccountServiceImpl accountService;
    private final RoleServiceImpl roleService;
    private final CurrencyServiceImpl currencyService;
    private final CardServiceImpl cardService;

    @Override
    @Transactional
    public void createUser(UserRegistrationForm form, String cardType, String cardName, ECurrency currency) throws RoleNotFoundException, CurrencyNotFoundException {
        // Проверяем, существует ли роль в БД
        Role roleUser = roleService.findByName(ERole.ROLE_USER).orElseThrow(() -> new RoleNotFoundException("Роль " + ERole.ROLE_USER.name() + " не найдена"));
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        // Проверяем, существует ли валюта в БД
        Currency currencyUser = currencyService.findByCode(currency).orElseThrow(() -> new CurrencyNotFoundException("Валюта " + currency.name() + " не найдена"));

        User user = userDao.create(form.getUsername(), form.getFirstName(), form.getLastName(), form.getEmail(), form.getPassword(), roles);
        accountService.createCardAndAccount(user, cardType, cardName, currencyUser);
    }

    @Override
    @Transactional
    public User updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Почта " + email + " не используется на сайте"));
        user.setResetPasswordToken(token);
        return userDao.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByResetPasswordToken(String token) {
        return userDao.findByResetPasswordToken(token);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String newPassword) {
        userDao.updatePassword(user, newPassword);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userDao.findByUsernameOrEmail(username, email);
    }

    public User findByCardNumber(String cardNumber) {
        Card userCard = cardService.findByCardNumber(cardNumber).orElseThrow(() -> new UsernameNotFoundException("Получатель не найден"));
        return userCard.getAccount().getUser();
    }

    @Override
    public AboutMeResponse toAboutMeResponse(User user) {
        return UserMapper.INSTANCE.userToAboutMeResponse(user);
    }

    @Override
    public UserResponse toUserResponse(User user) {
        return UserMapper.INSTANCE.userToUserResponse(user);
    }
}
