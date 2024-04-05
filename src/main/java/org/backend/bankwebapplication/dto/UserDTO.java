package org.backend.bankwebapplication.dto;

import java.util.List;

public record UserDTO(Long id, String username, String firstName, String lastName, String fullName, String email,
                      String createdAt,
                      Boolean isActive, Boolean isBlocked, List<AccountDTO> accounts) {
}
