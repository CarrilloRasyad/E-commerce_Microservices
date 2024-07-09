package com.example.transaction.service.dto;

import com.example.transaction.service.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long id;
    private float amount;
    private Long orderId;
    private LocalDateTime paymentDate;
    private String mode;
    private PaymentStatus status;
    private String referenceNumber;
}
