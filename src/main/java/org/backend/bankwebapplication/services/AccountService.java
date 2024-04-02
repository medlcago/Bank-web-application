package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.User;

public interface AccountService {
    Account getUserAccountByCurrency(User user, String currency);

    void createCardAndAccount(User user, String cardType, String cardName, String currency);
}
