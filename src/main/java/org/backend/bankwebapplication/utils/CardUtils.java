package org.backend.bankwebapplication.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CardUtils {
    /**
     * Генерирует случайный номер карты.
     *
     * @param formatted логическое значение, указывающее, нужно ли отформатировать сгенерированный номер карты
     * @param delimiter разделитель, используемый для форматирования номера карты (либо '-' либо ' ')
     * @return сгенерированный номер карты
     * @throws IllegalArgumentException если разделитель не является '-' или ' '
     */

    public String generateCardNumber(boolean formatted, String delimiter) {
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
            if (delimiter == null || (!delimiter.equals("-") && !delimiter.equals(" "))) {
                throw new IllegalArgumentException("Разделитель должен быть либо '-' либо ' '");
            }
            return formatCardNumber(cardNumberBuilder.toString(), delimiter);
        }
        return cardNumberBuilder.toString();
    }

    /**
     * Форматирует номер карты с использованием указанного разделителя.
     *
     * @param cardNumber номер карты, который нужно отформатировать
     * @param delimiter  разделитель, используемый для форматирования номера карты
     * @return отформатированный номер карты
     */
    public String formatCardNumber(String cardNumber, String delimiter) {
        StringBuilder formattedCardNumber = new StringBuilder();
        for (int i = 0; i < cardNumber.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formattedCardNumber.append(delimiter);
            }
            formattedCardNumber.append(cardNumber.charAt(i));
        }
        return formattedCardNumber.toString();
    }

    /**
     * Вычисляет контрольную сумму по алгоритму Луна.
     *
     * @param number номер, для которого нужно вычислить контрольную сумму
     * @return контрольная сумма по алгоритму Луна
     */
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
}
