package org.backend.bankwebapplication.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.backend.bankwebapplication.enums.TransactionType;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "transactios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }

}
