package org.backend.bankwebapplication.dto.responses;

import java.util.List;

public record TransactionHistoryResponse(Long total, List<TransactionResponse> rows) {

}
