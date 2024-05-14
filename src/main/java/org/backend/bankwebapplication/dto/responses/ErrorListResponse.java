package org.backend.bankwebapplication.dto.responses;

import java.util.Map;

public record ErrorListResponse(Map<String, String> errors) {
}

