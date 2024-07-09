package com.example.order.service.service;

import com.example.common.dto.OrderItemDTO;
import com.example.common.dto.OrdersDTO;
import com.example.common.status.OrdersStatus;
import com.example.order.service.config.KafkaConfig;
import com.example.order.service.dto.OrderItemResponseDTO;
import com.example.order.service.dto.OrdersRequestDTO;
import com.example.order.service.dto.OrdersResponseDTO;
import com.example.order.service.exception.OrdersNotFoundException;
import com.example.order.service.model.OrderItem;
import com.example.order.service.model.OrderStatus;
import com.example.order.service.model.Orders;
import com.example.order.service.repository.OrderItemRepository;
import com.example.order.service.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class  OrdersService {
    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrdersKafkaProducer ordersKafkaProducer;

    public Flux<OrdersResponseDTO> findAll() {
        return ordersRepository.findAll()
                .flatMap(this::mapOrderToResponseDTO);
    }

    public Mono<OrdersResponseDTO> findById(Long id) {
        return ordersRepository.findById(id)
                .switchIfEmpty(Mono.error(new OrdersNotFoundException(String.format("Order not found. ID: %s", id))))
                .flatMap(this::mapOrderToResponseDTO);
    }

    public Mono<OrdersResponseDTO> createOrder(OrdersRequestDTO ordersRequestDTO) {
        Orders order = Orders.builder()
                .billingAddress(ordersRequestDTO.getBillingAddress())
                .customerId(ordersRequestDTO.getCustomerId())
                .orderStatus(OrderStatus.CREATED.name())
                .paymentMethod(ordersRequestDTO.getPaymentMethod())
                .shippingAddress(ordersRequestDTO.getShippingAddress())
                .build();

        return ordersRepository.save(order)
                .flatMap(savedOrder -> {
                    List<OrderItem> orderItems = ordersRequestDTO.getOrderItems().stream()
                            .map(item -> OrderItem.builder()
                                    .price(item.getPrice())
                                    .productId(item.getProductId())
                                    .quantity(item.getQuantity())
                                    .orderId(savedOrder.getId())
                                    .build())
                            .collect(Collectors.toList());

                    return orderItemRepository.saveAll(orderItems).collectList()
                            .thenReturn(savedOrder);
                })
                .doOnNext(savedOrder1 -> {
                    try {
                        log.info("orders kafka: "+savedOrder1);
                        ordersKafkaProducer.sendOrdersEvent(KafkaConfig.ORDER_CREATED_TOPIC, savedOrder1);
                    }catch (Exception e) {
                        log.error("Failed to send order to Kafka: {}", e.getMessage());
                    }
                })
                .flatMap(this::mapOrderToResponseDTO);
    }

    public Mono<OrdersResponseDTO> updateOrderStatus(Long id, OrderStatus status) {
        return ordersRepository.findById(id)
                .flatMap(order -> {
                    order.setOrderStatus(status.name());
                    return ordersRepository.save(order);
                })
                .flatMap(this::mapOrderToResponseDTO);
    }

    private Mono<OrdersResponseDTO> mapOrderToResponseDTO(Orders order) {
        return orderItemRepository.findAll()
                .filter(item -> item.getOrderId().equals(order.getId()))
                .map(item -> OrderItemResponseDTO.builder()
                        .id(item.getId())
                        .price(item.getPrice())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .orderId(item.getOrderId())
                        .build())
                .collectList()
                .map(orderItems -> OrdersResponseDTO.builder()
                        .id(order.getId())
                        .billingAddress(order.getBillingAddress())
                        .customerId(order.getCustomerId())
                        .orderDate(order.getOrderDate())
                        .orderStatus(OrderStatus.valueOf(order.getOrderStatus()))
                        .paymentMethod(order.getPaymentMethod())
                        .shippingAddress(order.getShippingAddress())
                        .orderItems(orderItems)
                        .build());
    }
}


//@Service
//public class OrdersService {
//    @Autowired
//    OrdersRepository ordersRepository;
//
//    @Autowired
//    OrdersKafkaProducer ordersKafkaProducer;
//
//    public Flux<Orders> findAll(){
//        return ordersRepository.findAll();
//    }
//    public Mono<Orders> findById(Long id){
//        return ordersRepository.findById(id)
//                .switchIfEmpty(Mono.error(new OrdersNotFoundException(String.format("Order not found. ID: %s", id))));
//    }
//
////    public Mono<Orders> saveOrders(Orders orders) {
////        return ordersRepository.save(orders)
////                .doOnSuccess(savedOrders -> ordersKafkaProducer.sendOrdersEvent(savedOrders));
////    }
//    public Mono<Orders> createOrder(Orders orders) {
//        orders.setOrderStatus("CREATED");
//        return ordersRepository.save(orders)
//                .doOnSuccess(savedOrder -> ordersKafkaProducer.sendOrdersEvent("orders-created-topic", savedOrder));
//    }
//
//    public Mono<Orders> updateOrderStatus(Long id, String status) {
//        return ordersRepository.findById(id)
//                .flatMap(orders -> {
//                    orders.setOrderStatus(status);
//                    return ordersRepository.save(orders);
//                });
//    }


//    public Mono<Void> deleteById(Long id){
//        return ordersRepository.deleteById(id);
//    }
//    public Mono<Void> deleteAll() {
//        return ordersRepository.deleteAll();
//    }
//}