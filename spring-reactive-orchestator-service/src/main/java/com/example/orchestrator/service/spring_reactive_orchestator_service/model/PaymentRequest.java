package com.example.orchestrator.service.spring_reactive_orchestator_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long customerId;
    private Double amount;
    private Long orderId;
}