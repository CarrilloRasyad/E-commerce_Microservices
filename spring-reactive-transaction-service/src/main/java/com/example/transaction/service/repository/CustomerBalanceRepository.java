package com.example.transaction.service.repository;

import com.example.transaction.service.model.CustomerBalance;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerBalanceRepository extends R2dbcRepository<CustomerBalance, Long> {
    Mono<CustomerBalance> findByCustomerId(Long customerId);
}
