package org.backend.bankwebapplication.dto;

import org.backend.bankwebapplication.enums.CardType;
import org.backend.bankwebapplication.enums.ECurrency;

public record CreateAccountDTO(CardType cardType, ECurrency currencyType){
}
