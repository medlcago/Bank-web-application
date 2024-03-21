package org.backend.bankwebapplication.services;

public interface CardService {
    String generateCardNumber();

    int calculateLuhnChecksum(String number);

    boolean validateCardNumber(String cardNumber);
}
