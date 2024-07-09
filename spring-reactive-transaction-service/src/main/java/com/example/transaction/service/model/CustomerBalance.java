package com.example.transaction.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table("customer_balance")
public class CustomerBalance {
    @Id
    private Long id;
    private Long customerId;
    private float amount;
}
