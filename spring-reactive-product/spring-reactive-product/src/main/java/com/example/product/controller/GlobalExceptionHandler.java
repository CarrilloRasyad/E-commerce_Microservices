package com.example.product.controller;

import com.example.product.exception.ProductNotFoundException;
import com.example.product.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex){
        Mono<ErrorResponse> errResponse = Mono.just(new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        return errResponse;
    }
}
