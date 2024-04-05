package org.backend.bankwebapplication.dto.response;

import org.backend.bankwebapplication.dto.TransactionDTO;

import java.util.List;

public record TransactionHistoryResponse(Long total, List<TransactionDTO> rows) {

}
