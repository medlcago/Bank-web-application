package org.backend.bankwebapplication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardType {
    DEBIT("Дебетовая карта"),
    CREDIT("Кредитная карта");

    private final String description;
}
