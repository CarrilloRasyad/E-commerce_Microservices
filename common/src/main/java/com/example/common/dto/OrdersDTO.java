package com.example.common.dto;

import com.example.common.status.OrdersStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersDTO {
    private Long id;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Double totalAmount;
    private OrdersStatus status;

    private List<OrderItemDTO> orderItemDTO;

}
//    @Builder.Default
//    private List<OrderItemDTO> orderItemDTO = new ArrayList<>();
