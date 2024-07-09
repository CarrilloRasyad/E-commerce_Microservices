package com.example.orchestrator.service.spring_reactive_orchestator_service.service;

import com.example.common.dto.OrdersDTO;
import com.example.common.status.OrdersStatus;
import com.example.orchestrator.service.spring_reactive_orchestator_service.model.Orders;
import com.example.orchestrator.service.spring_reactive_orchestator_service.model.PaymentRequest;
import com.example.orchestrator.service.spring_reactive_orchestator_service.model.StatusUpdateRequest;
import com.example.orchestrator.service.spring_reactive_orchestator_service.model.StockRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrchestratorService {
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

//    @KafkaListener(topics = "orders-created-topic", groupId = "orders-service-group")
//    public void handleOrderCreated(OrdersDTO orderJson) throws JsonProcessingException {
//        log.info("masuk nih: {}", orderJson);
//            processOrder(orderJson).subscribe();
//    }

    public Mono<Void> processOrder(OrdersDTO orders) {
        return checkProductStock(orders)
                .flatMap(available -> available ? processPayment(orders) : Mono.just(false))
                .flatMap(paymentSuccess -> {
                    if (paymentSuccess) {
                        return updateOrderStatus(orders.getId(), "COMPLETED");
                    } else {
                        return rollbackProductStock(orders)
                                .then(updateOrderStatus(orders.getId(), "FAILED"));
                    }
                })
                .doOnSuccess(unused -> sendOrderStatus(orders))
                .then();
    }
//    private Mono<Boolean> checkProductStock(OrdersDTO orders) {
//        log.info("Checking stock for order: {}", orders);
//        return webClientBuilder.build()
//                .post()
//                .uri("http://localhost:8080/product/deduct")
//                .bodyValue(orders)
//                .retrieve()
//                .bodyToMono(OrdersDTO.class)
//                .map(response -> response.getStatus() == OrdersStatus.CREATED)
//                .doOnNext(result -> log.info("Stock check result: {}", result));
//    }

    private Mono<Boolean> checkProductStock(OrdersDTO orders) {
        log.info("order masuk: {}", orders);
        return webClientBuilder.build()
                .put()
                .uri("http://localhost:8080/product/deduct/{id}", orders.getOrderItemDTO().get(0).getProductId())
                .bodyValue(orders)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private Mono<Boolean> processPayment(OrdersDTO orders) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8082/payment/debit")
                .bodyValue(new PaymentRequest(orders.getCustomerId(), orders.getTotalAmount(), orders.getId()))
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    private Mono<Void> updateOrderStatus(Long orderId, String status) {
        return webClientBuilder.build()
                .put()
                .uri("http://localhost:8081/order/update")
                .bodyValue(new StatusUpdateRequest(orderId, status))
                .retrieve()
                .bodyToMono(Void.class);
    }

    private Mono<Void> rollbackProductStock(OrdersDTO orders) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/product/add")
                .bodyValue(new StockRequest(orders.getProductId(), orders.getQuantity()))
                .retrieve()
                .bodyToMono(Void.class);
    }

    private void sendOrderStatus(OrdersDTO orders) {
        try {
            String orderStatusJson = objectMapper.writeValueAsString(orders);
            kafkaTemplate.send("order-status-topic", orderStatusJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
