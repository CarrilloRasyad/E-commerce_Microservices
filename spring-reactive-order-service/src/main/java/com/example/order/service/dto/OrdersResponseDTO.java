package com.example.order.service.dto;

import com.example.order.service.model.OrderStatus;
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
public class OrdersResponseDTO {
    private Long id;
    private String billingAddress;
    private Long customerId;
    private Date orderDate;
    private OrderStatus orderStatus;
    private String paymentMethod;
    private String shippingAddress;
    private List<OrderItemResponseDTO> orderItems;
}