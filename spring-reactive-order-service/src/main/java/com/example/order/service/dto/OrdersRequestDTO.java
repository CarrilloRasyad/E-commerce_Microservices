package com.example.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersRequestDTO {
    private String billingAddress;
    private Long customerId;
    private String paymentMethod;
    private String shippingAddress;
    private List<OrderItemRequestDTO> orderItems;
}
