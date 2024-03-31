package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.AccountRepository;
import org.backend.bankwebapplication.services.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
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

    @Override
    public Account getAccountByCurrency(User user, String currency) {
        return user.getAccounts().stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Счет с валютой " + currency + " не найден"));
    }

    public void updateRecipientAccountBalance(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    public void updateSenderAccountBalance(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}
