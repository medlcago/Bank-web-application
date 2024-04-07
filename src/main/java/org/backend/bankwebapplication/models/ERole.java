package org.backend.bankwebapplication.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERole {
    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь"),
    ROLE_MODERATOR("Модератор");

    private final String description;
}