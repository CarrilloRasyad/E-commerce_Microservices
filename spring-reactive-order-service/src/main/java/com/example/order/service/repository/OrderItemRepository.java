package com.example.order.service.repository;

import com.example.order.service.model.OrderItem;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends R2dbcRepository<OrderItem, Long> {
}
