package com.example.order.service.service;

import com.example.order.service.model.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrdersKafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(OrdersKafkaProducer.class);

    private final KafkaTemplate<String, Orders> kafkaTemplate;

    @Autowired
    public OrdersKafkaProducer(KafkaTemplate<String, Orders> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrdersEvent(String topicName, Orders order) {
        log.info("Sending order event to Kafka: {}", order);
        kafkaTemplate.send(topicName, order.getId().toString(), order)
                .thenAccept(result -> log.info("Order sent to topic: {}", topicName))
                .exceptionally(ex -> {
                    log.error("Failed to send order to Kafka", ex);
                    return null;
                });
    }
}
