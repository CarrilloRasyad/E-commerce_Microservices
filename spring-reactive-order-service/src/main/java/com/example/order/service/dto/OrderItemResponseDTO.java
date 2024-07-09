package com.example.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private Long id;
    private float price;
    private Long productId;
    private Integer quantity;
    private Long orderId;
}
