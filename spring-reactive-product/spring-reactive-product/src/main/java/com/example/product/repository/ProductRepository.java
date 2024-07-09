package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

}
