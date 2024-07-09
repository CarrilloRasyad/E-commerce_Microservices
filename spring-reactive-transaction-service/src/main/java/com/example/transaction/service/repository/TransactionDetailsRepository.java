package com.example.transaction.service.repository;

import com.example.transaction.service.model.TransactionDetails;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends R2dbcRepository<TransactionDetails, Long> {
}
