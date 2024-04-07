package org.backend.bankwebapplication.dto;

import java.util.List;

public record AboutMeDTO(UserDTO user, List<AccountDTO> accounts) {
}
