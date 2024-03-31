package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.AccountRepository;
import org.backend.bankwebapplication.repository.UserRepository;
import org.backend.bankwebapplication.services.TransferService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void transferFunds(String senderUsername, String recipientUsername, BigDecimal amount, String currency) {
        User sender = userRepository.findByUsername(senderUsername).orElseThrow(() -> new UsernameNotFoundException("Отправитель не найден"));
        User recipient = userRepository.findByUsername(recipientUsername).orElseThrow(() -> new UsernameNotFoundException("Получатель не найден"));

        if (recipient.getUsername().equals(senderUsername)) {
            throw new IllegalArgumentException("Нельзя переводить деньги самому себе");
        }

        if (amount.compareTo(new BigDecimal("100000")) > 0) {
            throw new IllegalArgumentException("Максимальная сумма перевода не должна превышать 100 000 " + currency);
        }

        Account senderAccount = getAccountByCurrency(sender, currency);
        Account recipientAccount = getAccountByCurrency(recipient, currency);

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("На вашем счете недостаточно средств");
        }

        updateRecipientAccountBalance(recipientAccount, amount);
        updateSenderAccountBalance(senderAccount, amount);
    }

    private Account getAccountByCurrency(User user, String currency) {
        return user.getAccounts().stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Счет с валютой " + currency + " не найден"));
    }

    private void updateRecipientAccountBalance(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private void updateSenderAccountBalance(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }
}
