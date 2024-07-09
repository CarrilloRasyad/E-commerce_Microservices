package com.example.product.exception;

public class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String message) {
        super(message);
    }
    public ProductNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }
}
