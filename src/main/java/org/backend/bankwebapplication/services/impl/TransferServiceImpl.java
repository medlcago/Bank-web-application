package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.User;
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
    private final AccountServiceImpl accountService;

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

        Account senderAccount = accountService.getAccountByCurrency(sender, currency);
        Account recipientAccount = accountService.getAccountByCurrency(recipient, currency);

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("На вашем счете недостаточно средств");
        }

        accountService.updateRecipientAccountBalance(recipientAccount, amount);
        accountService.updateSenderAccountBalance(senderAccount, amount);
    }
}
