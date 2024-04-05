package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dto.TransactionDTO;
import org.backend.bankwebapplication.mappers.TransactionMapper;
import org.backend.bankwebapplication.models.Transaction;
import org.backend.bankwebapplication.models.User;
import org.backend.bankwebapplication.repository.TransactionRepository;
import org.backend.bankwebapplication.services.TransactionService;
import org.backend.bankwebapplication.utils.SortUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public void createTransaction(User sender, User receiver, BigDecimal amount, String currency, String type) {
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .currency(currency)
                .type(type)
                .build();
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> findByUserId(Long userId, int limit, int offset, String sort, String order) {
        int pageNumber = offset / limit;
        Sort sortCriteria = SortUtils.buildSort(sort, order);

        Pageable pageable = PageRequest.of(pageNumber, limit, sortCriteria);
        return transactionRepository.findBySenderIdOrReceiverId(userId, userId, pageable);
    }

    public List<TransactionDTO> toDTOList(List<Transaction> transactions) {
        return TransactionMapper.INSTANCE.toDTOList(transactions);
    }
}
