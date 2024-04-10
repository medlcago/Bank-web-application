package org.backend.bankwebapplication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    DEPOSIT("Депозит"),
    WITHDRAWAL("Снятие средств"),
    TRANSFER("Перевод средств"),
    OTHER("Другое");

    private final String description;
}
