package org.backend.bankwebapplication.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private String name;

    private String cardNumber;

    private Integer balance;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Card(String type, String name, String cardNumber, Integer balance, Currency currency, Account account) {
        this.type = type;
        this.name = name;
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.currency = currency;
        this.account = account;
    }
}
