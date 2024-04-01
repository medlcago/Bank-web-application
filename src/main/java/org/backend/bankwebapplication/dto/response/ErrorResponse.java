package org.backend.bankwebapplication.dto.response;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}

