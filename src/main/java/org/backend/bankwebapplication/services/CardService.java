package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.models.Card;

import java.util.Optional;

public interface CardService {
    Optional<Card> findByCardNumber(String cardNumber);
}
