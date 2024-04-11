package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.AboutMeDTO;
import org.backend.bankwebapplication.dto.UserDTO;
import org.backend.bankwebapplication.dto.forms.UserRegistrationForm;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.enums.ERole;
import org.backend.bankwebapplication.exceptions.CurrencyNotFoundException;
import org.backend.bankwebapplication.exceptions.RoleNotFoundException;
import org.backend.bankwebapplication.mappers.UserMapper;
import org.backend.bankwebapplication.models.*;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountServiceImpl accountService;
    private final RoleServiceImpl roleService;
    private final CurrencyServiceImpl currencyService;

    @Override
    @Transactional
    public void createUser(UserRegistrationForm form, String cardType, String cardName, ECurrency currency) throws RoleNotFoundException, CurrencyNotFoundException {
        // Проверяем, существует ли роль в БД
        Role roleUser = roleService.findByName(ERole.ROLE_USER).orElseThrow(() -> new RoleNotFoundException("Роль " + ERole.ROLE_USER.name() + " не найдена"));
        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        // Проверяем, существует ли валюта в БД
        Currency currencyUser = currencyService.findByCode(currency).orElseThrow(() -> new CurrencyNotFoundException("Валюта " + currency.name() + " не найдена"));

        User user = User.builder()
                .username(form.getUsername())
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .password(passwordEncoder.encode(form.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        accountService.createCardAndAccount(user, cardType, cardName, currencyUser);
    }

    @Override
    @Transactional
    public User updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Почта " + email + " не используется на сайте"));
        user.setResetPasswordToken(token);
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    @Transactional
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email);
    }

    @Override
    public AboutMeDTO toAboutMeDTO(User user) {
        return UserMapper.INSTANCE.userToAboutMeDTO(user);
    }

    @Override
    public UserDTO toUserDTO(User user) {
        return UserMapper.INSTANCE.userToUserDTO(user);
    }
}
