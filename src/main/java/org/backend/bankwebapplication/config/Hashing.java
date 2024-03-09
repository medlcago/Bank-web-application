package org.backend.bankwebapplication.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class Hashing implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }

    public byte getSalt(){
        return 0;
    }
}
