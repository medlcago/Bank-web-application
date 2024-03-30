package org.backend.bankwebapplication.services;

public interface CardService {
    String generateCardNumber(boolean formatted, String delimiter);

    int calculateLuhnChecksum(String number);

    boolean validateCardNumber(String cardNumber);
}
