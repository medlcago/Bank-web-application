package org.backend.bankwebapplication.services.impl;

import org.backend.bankwebapplication.services.CardService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CardServiceImpl implements CardService {
    @Override
    public String generateCardNumber(boolean formatted) {
        Random random = new Random();
        String issuerIdentificationNumber = "4"; // Идентификационный номер эмитента карты
        StringBuilder cardNumberBuilder = new StringBuilder(issuerIdentificationNumber);

        for (int i = 0; i < 15; i++) {
            int digit = random.nextInt(10);
            cardNumberBuilder.append(digit);
        }

        int checksum = calculateLuhnChecksum(cardNumberBuilder.toString());
        int lastDigit = (Integer.parseInt(String.valueOf(cardNumberBuilder.charAt(cardNumberBuilder.length() - 1))) + checksum) % 10;
        cardNumberBuilder.setCharAt(cardNumberBuilder.length() - 1, Character.forDigit(lastDigit, 10));

        if (formatted) {
            return formatCardNumber(cardNumberBuilder.toString());
        }
        return cardNumberBuilder.toString();
    }

    private String formatCardNumber(String cardNumber) {
        StringBuilder formattedCardNumber = new StringBuilder();
        for (int i = 0; i < cardNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedCardNumber.append("-");
            }
            formattedCardNumber.append(cardNumber.charAt(i));
        }
        return formattedCardNumber.toString();
    }


    @Override
    public int calculateLuhnChecksum(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }

    @Override
    public boolean validateCardNumber(String cardNumber) {
        cardNumber = cardNumber.replaceAll("\\D", "");
        if (cardNumber.length() != 16) {
            return false;
        }

        int checksum = calculateLuhnChecksum(cardNumber);
        return checksum == 0;
    }
}
