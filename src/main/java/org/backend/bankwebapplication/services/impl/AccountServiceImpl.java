package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dao.AccountDao;
import org.backend.bankwebapplication.dto.responses.AccountResponse;
import org.backend.bankwebapplication.entities.Account;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.exceptions.AccountNotFoundException;
import org.backend.bankwebapplication.mappers.AccountMapper;
import org.backend.bankwebapplication.services.AccountService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    @Override
    public Account getUserAccountByCurrency(User user, Currency currency) throws AccountNotFoundException {
        return accountDao.getUserAccountByCurrency(user, currency)
                .orElseThrow(() -> new AccountNotFoundException("Пользователь " + user.getUsername() + " не имеет счета с валютой " + currency.getCode().name()));
    }

    @Transactional
    @CacheEvict(value = {"AccountService::findByUserId", "TransactionService::findByUserId"}, key = "#account.user.id")
    public void addBalanceToAccount(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().add(amount);
        account.setBalance(newBalance);
        accountDao.save(account);
    }

    @Transactional
    @CacheEvict(value = {"AccountService::findByUserId", "TransactionService::findByUserId"}, key = "#account.user.id")
    public void subtractBalanceFromAccount(Account account, BigDecimal amount) {
        BigDecimal newBalance = account.getBalance().subtract(amount);
        account.setBalance(newBalance);
        accountDao.save(account);
    }

    @Override
    @Transactional
    @CacheEvict(value = "AccountService::findByUserId", key = "#user.id")
    public void createCardAndAccount(User user, String cardType, String cardName, Currency currency) {
        accountDao.createCardAndAccount(user, cardType, cardName, currency);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "AccountService::findByUserId", key = "#userId")
    public List<Account> findByUserId(Long userId) {
        return accountDao.findByUserId(userId);
    }

    @Override
    public AccountResponse toAccountResponse(Account account) {
        return AccountMapper.INSTANCE.accountToAccountResponse(account);
    }

    @Override
    public List<AccountResponse> toAccountResponseList(List<Account> accounts) {
        return AccountMapper.INSTANCE.accountsToAccountResponseList(accounts);
    }
}
