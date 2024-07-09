package com.example.transaction.service.dto;

import com.example.transaction.service.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestDTO {
    private float amount;
    private Long orderId;
    private String mode;
    private PaymentStatus status;
    private String referenceNumber;
    private List<CustomerRequestDTO> customerRequest;
}
