package com.example.order.service.repository;

import com.example.order.service.model.Orders;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends R2dbcRepository<Orders, Long> {
}
