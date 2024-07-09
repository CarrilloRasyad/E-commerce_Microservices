package com.example.transaction.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDTO {
    private Long id;
    private Long customerId;
    private float amount;
}
