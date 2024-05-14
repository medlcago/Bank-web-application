package org.backend.bankwebapplication.security.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Не удалось найти учетную запись " + username));
        return UserDetailsImpl.build(user);
    }
}