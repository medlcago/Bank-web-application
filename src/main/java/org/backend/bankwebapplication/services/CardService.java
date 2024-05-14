package org.backend.bankwebapplication.services;

import org.backend.bankwebapplication.entities.Card;

import java.util.Optional;

public interface CardService {
    Optional<Card> findByCardNumber(String cardNumber);
}
