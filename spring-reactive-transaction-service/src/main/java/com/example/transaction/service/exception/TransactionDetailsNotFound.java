package com.example.transaction.service.exception;

public class TransactionDetailsNotFound extends Exception{
    public TransactionDetailsNotFound (String message) {
        super(message);
    }
    public TransactionDetailsNotFound (String message, Throwable cause) {
        super(message, cause);
    }
    public TransactionDetailsNotFound (Throwable cause) {
        super(cause);
    }
}
