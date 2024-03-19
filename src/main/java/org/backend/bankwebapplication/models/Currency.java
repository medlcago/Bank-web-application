package org.backend.bankwebapplication.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "currencies")
@Data
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "currency")
    private List<Card> cards;

    public Currency(String code, String name, List<Card> cards) {
        this.code = code;
        this.name = name;
        this.cards = cards;
    }
}