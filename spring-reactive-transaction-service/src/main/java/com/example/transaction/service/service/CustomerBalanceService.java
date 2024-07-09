package com.example.transaction.service.service;

import com.example.common.dto.BalanceDTO;
import com.example.transaction.service.exception.TransactionDetailsNotFound;
import com.example.transaction.service.model.CustomerBalance;
import com.example.transaction.service.repository.CustomerBalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerBalanceService {
    @Autowired
    CustomerBalanceRepository cbrepo;

    public Flux<CustomerBalance> findAllCustBalance(){
        return cbrepo.findAll()
                .switchIfEmpty(Mono.error(new TransactionDetailsNotFound(String.format("Tidak ada customer balance"))));
    }

    public Mono<CustomerBalance> findCustBalanceById(Long id) {
        return cbrepo.findById(id)
                .switchIfEmpty(Mono.error(new TransactionDetailsNotFound(String.format("Tidak menemukan id customer"))));
    }

    public Mono<CustomerBalance> saveCustBalance(BalanceDTO balanceDTO) {
        CustomerBalance customerBalance = CustomerBalance.builder()
                .amount(balanceDTO.getAmount())
                .build();
        return cbrepo.save(customerBalance)
                .flatMap(savedCustomerBalance -> {
                    savedCustomerBalance.setCustomerId(savedCustomerBalance.getId());
                    return cbrepo.findById(savedCustomerBalance.getId())
                            .flatMap(custbalance -> {
                                custbalance.setCustomerId(custbalance.getId());
                                return cbrepo.save(custbalance);
                            });
                })
                .doOnError(error -> log.error("Error occurred: " + error.getMessage()));
    }
}
