package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.Card;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.AccountRepository;
import org.backend.bankwebapplication.services.AccountService;
import org.backend.bankwebapplication.utils.AccountUtils;
import org.backend.bankwebapplication.utils.CardUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public Account getUserAccountByCurrency(User user, String currency) {
        return user.getAccounts().stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Счет с валютой " + currency + " не найден"));
    }

    @Transactional
    public void updateRecipientAccountBalance(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Transactional
    public void updateSenderAccountBalance(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void createCardAndAccount(User user, String cardType, String cardName, String currency) {
        // Создание карты
        Card card = Card.builder()
                .type(cardType)
                .name(cardName)
                .cardNumber(CardUtils.generateCardNumber(true, "-"))
                .build();

        // Создание счета и связь его с картой и пользователем
        Account account = Account.builder()
                .accountNumber(AccountUtils.generateAccountNumber())
                .currency(currency.toUpperCase())
                .card(card)
                .user(user)
                .build();

        accountRepository.save(account);
    }
}
