package com.example.order.service.controller;

import com.example.order.service.dto.OrdersRequestDTO;
import com.example.order.service.dto.OrdersResponseDTO;
import com.example.order.service.model.OrderStatus;
import com.example.order.service.model.Orders;
import com.example.order.service.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class  OrdersController {
    @Autowired
    OrdersService ordersService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<OrdersResponseDTO> getAll() {
        return ordersService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<OrdersResponseDTO>> getOrdersById(@PathVariable("id") Long id) {
        return ordersService.findById(id)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/create")
    public Mono<OrdersResponseDTO> createOrder(@RequestBody OrdersRequestDTO orderRequestDTO) {
        return ordersService.createOrder(orderRequestDTO);
    }

    @PutMapping("/update/{id}")
    public Mono<OrdersResponseDTO> updateOrderStatus(@PathVariable Long id, @RequestBody String orderStatus) {
        return ordersService.updateOrderStatus(id, OrderStatus.valueOf(orderStatus));
    }
}

//    @DeleteMapping("/orders/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Mono<Void> deleteOrders(@PathVariable("id") Long id) {
//        return ordersService.deleteById(id);
//    }
//
//    @DeleteMapping("/orders")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public Mono<Void> deleteAll() {
//        return ordersService.deleteAll();
//    }
//}
