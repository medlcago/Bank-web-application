package org.backend.bankwebapplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "email"})
})
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"id", "username", "email"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 5, max = 16, message = "Имя пользователя должно содержать от 5 до 16 символов")
    @NonNull
    private String username;

    @Length(min = 2, max = 64, message = "Имя должно содержать от 2 до 64 символов")
    @NonNull
    private String firstName;

    @Length(min = 2, max = 64, message = "Фамилия должна содержать от 2 до 64 символов.")
    @NonNull
    private String lastName;

    @Length(max = 128, message = "Электронная почта должна содержать не более 128 символов")
    @Email(message = "Электронная почта должна быть действующей")
    @NonNull
    private String email;

    @Length(min = 6, message = "Пароль должен быть не менее 6 символов")
    @NonNull
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private Boolean isBlocked = false;

    private String resetPasswordToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> receivedTransactions;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public String getCreatedAt() {
        return createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss"));
    }
}