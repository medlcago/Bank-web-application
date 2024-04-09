package org.backend.bankwebapplication.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountUtils {
    /**
     * Генерирует случайный номер счета в формате XXX-XXX-XXX, где X - это либо цифра, либо буква.
     *
     * @return сгенерированный номер счета
     */
    public String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder();
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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
