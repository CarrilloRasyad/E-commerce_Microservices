package com.example.order.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    private Long id;

    private String billingAddress;
    private Long customerId;
    private Date orderDate;
    private String orderStatus;
    private String paymentMethod;
    private String shippingAddress;

}
