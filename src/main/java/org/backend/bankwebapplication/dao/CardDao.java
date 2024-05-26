package org.backend.bankwebapplication.dao;

import lombok.RequiredArgsConstructor;
import org.backend.bankwebapplication.entities.Card;
import org.backend.bankwebapplication.repository.CardRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardDao {
    private final CardRepository cardRepository;

    public Optional<Card> findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }
}
