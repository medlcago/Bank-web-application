package org.backend.bankwebapplication.exceptions;

public class MaxTransferAmountExceededException extends RuntimeException {
    public MaxTransferAmountExceededException(String message) {
        super(message);
    }
}