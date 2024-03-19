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

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private Integer balance = 0;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public void setBalance(Integer balance) {
        if (balance == null || balance < 0) {
            throw new IllegalArgumentException("Balance must not be null and must be greater than or equal to 0.");
        }
        this.balance = balance;
    }
}
