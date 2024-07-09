package com.example.orchestrator.service.spring_reactive_orchestator_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
    private Long productId;
    private Integer quantity;
}
