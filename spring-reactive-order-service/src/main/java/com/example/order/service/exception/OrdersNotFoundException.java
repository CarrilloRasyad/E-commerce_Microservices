package com.example.order.service.exception;

public class OrdersNotFoundException extends Exception{
    public OrdersNotFoundException (String message) {
        super(message);
    }
    public OrdersNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public OrdersNotFoundException(Throwable cause) {
        super(cause);
    }
}
