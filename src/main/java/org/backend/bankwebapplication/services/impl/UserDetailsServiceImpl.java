package org.backend.bankwebapplication.services.impl;

import jakarta.transaction.Transactional;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Не удалось найти учетную запись " + username));
        return UserDetailsImpl.build(user);
    }
}