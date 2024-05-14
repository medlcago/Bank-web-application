package org.backend.bankwebapplication.dto.responses;

import java.util.List;

public record AboutMeResponse(UserResponse user, List<AccountResponse> accounts) {
}
