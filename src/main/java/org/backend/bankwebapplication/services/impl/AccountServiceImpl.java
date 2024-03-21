package org.backend.bankwebapplication.services.impl;

import org.backend.bankwebapplication.services.AccountService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * Генерирует случайный номер счета в формате XXX-XXX-XXX, где X - это либо цифра, либо буква.
     *
     * @return сгенерированный номер счета
     */
    @Override
    public String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            char character = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            accountNumber.append(character);
        }

        accountNumber.insert(3, "-");
        accountNumber.insert(7, "-");
        return accountNumber.toString();
    }
}
