package org.backend.bankwebapplication.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserResponse(@JsonProperty("id") Long id,
                           @JsonProperty("username") String username,
                           @JsonProperty("first_name") String firstName,
                           @JsonProperty("last_name") String lastName,
                           @JsonProperty("full_name") String fullName,
                           @JsonProperty("email") String email,
                           @JsonProperty("created_at") String createdAt,
                           @JsonProperty("is_active") Boolean isActive,
                           @JsonProperty("is_blocked") Boolean isBlocked,
                           @JsonProperty("roles") List<String> roles) {
}
