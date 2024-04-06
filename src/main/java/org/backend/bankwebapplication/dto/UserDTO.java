package org.backend.bankwebapplication.dto;

public record UserDTO(Long id, String username, String firstName, String lastName, String fullName, String email,
                      String createdAt,
                      Boolean isActive, Boolean isBlocked) {
}
