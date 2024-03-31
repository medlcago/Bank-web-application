package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.models.Account;
import org.backend.bankwebapplication.models.User;

public interface AccountService {
    String generateAccountNumber();

    Account getAccountByCurrency(User user, String currency);
}
