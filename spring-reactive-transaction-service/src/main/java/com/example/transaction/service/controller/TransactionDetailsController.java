package com.example.transaction.service.controller;

import com.example.transaction.service.exception.TransactionDetailsNotFound;
import com.example.transaction.service.model.PaymentStatus;
import com.example.transaction.service.model.TransactionDetails;
import com.example.transaction.service.service.TransactionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class TransactionDetailsController {
    @Autowired
    TransactionDetailsService service;


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<TransactionDetails> getAllTransaction() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionDetails>> getTransactionById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(transactionDetails -> ResponseEntity.ok(transactionDetails));
    }
    @PostMapping("/debit")
    public Mono<TransactionDetails> debitPayment(@RequestParam Long customerId, @RequestParam float amount, @RequestParam Long orderId, PaymentStatus status) {
        return service.debitPayment(customerId, amount, orderId, status);
    }

    @PostMapping("/credit")
    public Mono<TransactionDetails> creditPayment(@RequestParam Long customerId, @RequestParam float amount, @RequestParam Long orderId, PaymentStatus status) {
        return service.creditPayment(customerId, amount, orderId, status);
    }

    @DeleteMapping("/transaction/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteById(@PathVariable("id") Long id) {
        return service.deleteById(id);
    }
    @DeleteMapping("/transaction/del")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAll(){
        return service.deleteAll();
    }

}

//    @PostMapping("/transaction")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<TransactionDetails> createTransaction(@RequestBody TransactionDetails transactionDetails) {
//        return service.save(transactionDetails);
//    }
