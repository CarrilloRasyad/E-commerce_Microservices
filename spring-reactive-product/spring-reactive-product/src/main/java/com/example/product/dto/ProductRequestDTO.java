package com.example.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {
    private String name;
    private float price;
    private String category;
    private String description;
    private String imageUrl;
    private Integer stockQuantity;
}
