package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dao.TransactionDao;
import org.backend.bankwebapplication.dto.responses.TransactionResponse;
import org.backend.bankwebapplication.entities.Currency;
import org.backend.bankwebapplication.entities.Transaction;
import org.backend.bankwebapplication.entities.User;
import org.backend.bankwebapplication.enums.TransactionType;
import org.backend.bankwebapplication.mappers.TransactionMapper;
import org.backend.bankwebapplication.services.TransactionService;
import org.backend.bankwebapplication.utils.SortUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final SortUtils sortUtils;
    private final TransactionDao transactionDao;

    @Override
    @Transactional
    public void createTransaction(User sender, User receiver, BigDecimal amount, Currency currency, TransactionType type) {
        transactionDao.create(sender, receiver, amount, currency, type);
    }

    @Override
    @Transactional(readOnly = true)
    @CachePut(value = "TransactionService::findByUserId", key = "#userId")
    public Page<Transaction> findByUserId(Long userId, int limit, int offset, String sort, String order) {
        int pageNumber = offset / limit;
        Sort sortCriteria = sortUtils.buildSort(sort, order);
        Pageable pageable = sortUtils.buildPageable(pageNumber, limit, sortCriteria);

        return transactionDao.findByUserId(userId, pageable);
    }

    @Override
    public List<TransactionResponse> toTransactionResponseList(List<Transaction> transactions) {
        return TransactionMapper.INSTANCE.transactionsToTransactionResponseList(transactions);
    }
}
