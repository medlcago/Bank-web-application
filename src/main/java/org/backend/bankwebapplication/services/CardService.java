package org.backend.bankwebapplication.services;

public interface CardService {
    String generateCardNumber(boolean formatted);

    int calculateLuhnChecksum(String number);

    boolean validateCardNumber(String cardNumber);
}
