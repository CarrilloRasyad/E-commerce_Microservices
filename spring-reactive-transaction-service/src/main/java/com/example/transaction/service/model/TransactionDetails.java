package com.example.transaction.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDetails {
    @Id
    private Long id;
    private float amount;
    private Long orderId;
    private LocalDateTime paymentDate;
    private String mode;
    private PaymentStatus status;
    private String referenceNumber;
}
