package org.backend.bankwebapplication.dto.requests;

import org.backend.bankwebapplication.enums.CardType;
import org.backend.bankwebapplication.enums.ECurrency;

public record CreateAccountRequest(CardType cardType, ECurrency currencyType){
}
