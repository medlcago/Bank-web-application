package org.backend.bankwebapplication.exceptions;

public class SelfTransferException extends RuntimeException {
    public SelfTransferException(String message) {
        super(message);
    }
}
