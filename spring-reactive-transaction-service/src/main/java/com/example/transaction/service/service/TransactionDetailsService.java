package com.example.transaction.service.service;

import com.example.transaction.service.exception.TransactionDetailsNotFound;
import com.example.transaction.service.model.PaymentStatus;
import com.example.transaction.service.model.TransactionDetails;
import com.example.transaction.service.repository.CustomerBalanceRepository;
import com.example.transaction.service.repository.TransactionDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionDetailsService {
    @Autowired
    TransactionDetailsRepository repo;

    @Autowired
    CustomerBalanceRepository custrepo;

    public Flux<TransactionDetails> findAll() {
        return repo.findAll();
    }
    public Mono<TransactionDetails> findById(Long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new TransactionDetailsNotFound(String.format("Transaction no found. ID: %s", id))));
    }
    public Mono<TransactionDetails> save(TransactionDetails transactionDetails) {
        return repo.save(transactionDetails)
                .switchIfEmpty(Mono.error(new TransactionDetailsNotFound(String.format("Tidak bisa nambahin transaksi/payment"))));
    }

    public Mono<TransactionDetails> debitPayment(Long customerId, float amount, Long orderId, PaymentStatus status) {
        return custrepo.findByCustomerId(customerId)
            .flatMap(balance -> {
                if (balance.getAmount() >= amount) {
                    balance.setAmount(balance.getAmount() - amount);
                    return custrepo.save(balance)
                            .then(createTransaction(customerId, amount, orderId, status.APPROVED));
                } else {
                    return createTransaction(customerId, amount, orderId, status.REJECTED );
                }
            });
    }

    public Mono<TransactionDetails> creditPayment(Long customerId, float amount, Long orderId, PaymentStatus status) {
        return custrepo.findByCustomerId(customerId)
                .flatMap(balance -> {
                    balance.setAmount(balance.getAmount() + amount);
                    return custrepo.save(balance)
                            .then(createTransaction(customerId, amount, orderId, status.APPROVED));
                });
    }


    private Mono<TransactionDetails> createTransaction(Long id, float amount, Long orderId, PaymentStatus status) {
        TransactionDetails transaction = new TransactionDetails();
        transaction.setId(id);
        transaction.setAmount(amount);
        transaction.setOrderId(orderId);
        transaction.setPaymentDate(LocalDateTime.now());
        transaction.setMode("BALANCE");
        transaction.setStatus(status);
        transaction.setReferenceNumber(transaction.getReferenceNumber());
        return repo.save(transaction);
    }
    public Mono<Void> deleteById(Long id) {
        return repo.deleteById(id);
    }
    public Mono<Void> deleteAll() {
        return repo.deleteAll();
    }
}

//    public Mono<TransactionDetails> save(TransactionDetails transactionDetails) {
//        return repo.save(transactionDetails);
//    }

//    public Mono<TransactionDetails> processPayment(TransactionDetails transactionDetails) {
//        return custrepo.findByCustomerId(transactionDetails.getCustomerId())
//                .flatMap(balance -> {
//                    if (balance.getAmount() >= transactionDetails.getAmount()) {
//                        balance.setAmount(balance.getAmount() - transactionDetails.getAmount());
//                        transactionDetails.setStatus("APPROVED");
//                        transactionDetails.setPaymentDate(LocalDateTime.now());
//                        return custrepo.save(balance)
//                                .then(repo.save(transactionDetails));
//                    } else {
//                        transactionDetails.setStatus("REJECTED");
//                        return repo.save(transactionDetails);
//                    }
//                });
//    }

//public Mono<Boolean> processPayment(Long customerId, float amount, Long orderId) {
//    return custrepo.findByCustomerId(customerId)
//            .flatMap(balance -> {
//                if (balance.getAmount() >= amount) {
//                    balance.setAmount(balance.getAmount() - amount);
//                    return custrepo.save(balance)
//                            .then(createTransaction(customerId, amount, orderId, "APPROVED"))
//                            .thenReturn(true);
//                } else {
//                    return createTransaction(customerId, amount, orderId, "REJECTED")
//                            .thenReturn(false);
//                }
//            });
//}
