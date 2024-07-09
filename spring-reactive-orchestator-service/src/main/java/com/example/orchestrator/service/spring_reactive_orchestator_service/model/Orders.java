package com.example.orchestrator.service.spring_reactive_orchestator_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Long id;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Double totalAmount;
    private String status;
}