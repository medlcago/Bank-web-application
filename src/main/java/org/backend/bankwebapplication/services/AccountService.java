package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.dto.responses.AccountResponse;
import org.backend.bankwebapplication.entities.Account;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.entities.User;

import java.util.List;

public interface AccountService {
    Account getUserAccountByCurrency(User user, Currency currency);

    void createCardAndAccount(User user, String cardType, String cardName, Currency currency);

    List<Account> findByUserId(Long userId);

    AccountResponse toAccountResponse(Account account);

    List<AccountResponse> toAccountResponseList(List<Account> accounts);
}
