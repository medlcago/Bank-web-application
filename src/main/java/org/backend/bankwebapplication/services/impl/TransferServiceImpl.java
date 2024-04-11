package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.enums.ECurrency;
import org.backend.bankwebapplication.enums.TransactionType;
import org.backend.bankwebapplication.exceptions.CurrencyNotFoundException;
import org.backend.bankwebapplication.exceptions.InsufficientFundsException;
import org.backend.bankwebapplication.exceptions.MaxTransferAmountExceededException;
import org.backend.bankwebapplication.exceptions.SelfTransferException;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.Currency;
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
    private final CurrencyServiceImpl currencyService;

    @Override
    @Transactional
    public void transferFunds(String senderUsername, String receiverUsername, BigDecimal amount, ECurrency currency) {
        User sender = userService.findByUsername(senderUsername).orElseThrow(() -> new UsernameNotFoundException("Отправитель не найден"));
        User receiver = userService.findByUsername(receiverUsername).orElseThrow(() -> new UsernameNotFoundException("Получатель не найден"));
        Currency currencyObj = currencyService.findByCode(currency).orElseThrow(() -> new CurrencyNotFoundException("Валюта не найдена"));

        if (receiver.getUsername().equals(senderUsername)) {
            throw new SelfTransferException("Нельзя переводить деньги самому себе");
        }

        if (amount.compareTo(new BigDecimal("100000")) > 0) {
            throw new MaxTransferAmountExceededException("Максимальная сумма перевода не должна превышать 100 000 " + currencyObj.getCode());
        }

        Account senderAccount = accountService.getUserAccountByCurrency(sender, currencyObj);
        Account receiverAccount = accountService.getUserAccountByCurrency(receiver, currencyObj);

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("На вашем счете недостаточно средств");
        }

        transactionService.createTransaction(sender, receiver, amount, currencyObj, TransactionType.TRANSFER);
        accountService.updateReceiverAccountBalance(receiverAccount, amount);
        accountService.updateSenderAccountBalance(senderAccount, amount);
    }
}
