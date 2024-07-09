package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailsDTO {

    private Float amount;
    private Long order_id;

    private String mode;
    private String status;
    private String reference_number;

}
