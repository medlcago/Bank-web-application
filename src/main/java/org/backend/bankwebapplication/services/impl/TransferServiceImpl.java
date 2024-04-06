package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.services.TransferService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final UserServiceImpl userService;
    private final AccountServiceImpl accountService;
    private final TransactionServiceImpl transactionService;

    @Override
    @Transactional
    public void transferFunds(String senderUsername, String receiverUsername, BigDecimal amount, String currency) {
        User sender = userService.findByUsername(senderUsername).orElseThrow(() -> new UsernameNotFoundException("Отправитель не найден"));
        User receiver = userService.findByUsername(receiverUsername).orElseThrow(() -> new UsernameNotFoundException("Получатель не найден"));

        if (receiver.getUsername().equals(senderUsername)) {
            throw new IllegalArgumentException("Нельзя переводить деньги самому себе");
        }

        if (amount.compareTo(new BigDecimal("100000")) > 0) {
            throw new IllegalArgumentException("Максимальная сумма перевода не должна превышать 100 000 " + currency);
        }

        Account senderAccount = accountService.getUserAccountByCurrency(sender, currency);
        Account recipientAccount = accountService.getUserAccountByCurrency(receiver, currency);

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("На вашем счете недостаточно средств");
        }

        transactionService.createTransaction(sender, receiver, amount, currency, "Перевод средств");
        accountService.updateRecipientAccountBalance(recipientAccount, amount);
        accountService.updateSenderAccountBalance(senderAccount, amount);
    }
}
