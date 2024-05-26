package org.backend.bankwebapplication.dao;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.entities.Account;
import org.backend.bankwebapplication.entities.Card;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.repository.AccountRepository;
import org.backend.bankwebapplication.utils.AccountUtils;
import org.backend.bankwebapplication.utils.CardUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDao {
    private final AccountRepository accountRepository;
    private final CardUtils cardUtils;
    private final AccountUtils accountUtils;

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> getUserAccountByCurrency(User user, Currency currency) {
        return user.getAccounts().stream()
                .filter(account -> account.getCurrency().equals(currency))
                .findFirst();
    }

    public void createCardAndAccount(User user, String cardType, String cardName, Currency currency) {
        // Создание карты
        Card card = Card.builder()
                .type(cardType)
                .name(cardName)
                .cardNumber(cardUtils.generateCardNumber(true, "-"))
                .build();

        // Создание счета и связь его с картой и пользователем
        Account account = Account.builder()
                .accountNumber(accountUtils.generateAccountNumber())
                .currency(currency)
                .card(card)
                .user(user)
                .build();
        save(account);
    }

    public List<Account> findByUserId(Long userId) {
        return accountRepository.findByUserIdOrderById(userId);
    }

}
