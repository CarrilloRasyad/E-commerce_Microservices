package com.example.order.service.exception;

public class OrderItemNotFoundException extends Exception{
    public OrderItemNotFoundException(String message){
        super(message);
    }
    public OrderItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public OrderItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
