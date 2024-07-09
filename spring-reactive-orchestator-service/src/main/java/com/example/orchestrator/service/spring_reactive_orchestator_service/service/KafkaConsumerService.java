package com.example.orchestrator.service.spring_reactive_orchestator_service.service;


import com.example.common.dto.OrdersDTO;
import com.example.orchestrator.service.spring_reactive_orchestator_service.model.Orders;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final ObjectMapper objectMapper;
    private final OrchestratorService orchestratorService;

//    @KafkaListener(topics = "orders-created-topic", groupId = "orders-service-group")
//    public void handleOrderCreated(String message) {
//        try {
//            OrdersDTO orders = objectMapper.readValue(message, OrdersDTO.class);
//            log.info("Order received: {}", orders);
//            orchestratorService.processOrder(orders).subscribe();
//        } catch (Exception e) {
//            log.error("Error processing order message", e);
//        }
//    }

    @KafkaListener(topics = "orders-created-topic", groupId = "orders-service-group")
    public void kafkaListenerContainerFactory(String message) {
        try {
            OrdersDTO orders = objectMapper.readValue(message, OrdersDTO.class);
            log.info("Order masuk ke listen: {}",message);
            orchestratorService.processOrder(orders).subscribe();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}