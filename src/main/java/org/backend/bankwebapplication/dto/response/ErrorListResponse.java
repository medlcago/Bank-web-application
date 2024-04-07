package org.backend.bankwebapplication.dto.response;

import java.util.Map;

public record ErrorListResponse(Map<String, String> errors) {
}

