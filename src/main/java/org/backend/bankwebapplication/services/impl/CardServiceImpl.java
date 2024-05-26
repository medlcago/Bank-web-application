package org.backend.bankwebapplication.services.impl;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.dao.CardDao;
import org.backend.bankwebapplication.entities.Card;
import org.backend.bankwebapplication.services.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardDao cardDao;

    @Override
    @Transactional(readOnly = true)
    public Optional<Card> findByCardNumber(String cardNumber) {
        return cardDao.findByCardNumber(cardNumber);
    }
}
