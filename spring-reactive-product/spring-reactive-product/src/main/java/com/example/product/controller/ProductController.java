package com.example.product.controller;

import com.example.common.dto.OrdersDTO;
import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;
import com.example.product.model.Product;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Product> getProductById(@PathVariable("id") Long id) {
        return productService.findById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.save(productRequestDTO);
    }


    @PostMapping("/deduct/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OrdersDTO> deductProduct(@PathVariable("id") Long productId, @RequestBody OrdersDTO ordersDTO) {
        ordersDTO.getOrderItemDTO().forEach(orderItemDTO -> {
            orderItemDTO.setProductId(productId); // Set productId from path variable
        });
        return productService.deductProduct(ordersDTO);
    }
    //@PostMapping("/deduct")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<OrdersDTO> deductProduct(@RequestBody OrdersDTO ordersDTO) {
//        return productService.deductProduct(ordersDTO);
//    }

//    public Mono<OrdersDTO> deductProduct(@PathVariable("id") @RequestBody OrdersDTO ordersDTO) {
//        return productService.deductProduct(ordersDTO);
//    }
}



//    @GetMapping("/all")
//    @ResponseStatus(HttpStatus.OK)
//    public Flux<Product> getAllProducts() {
//        return productService.findAll();
//    }
//
//
//    @GetMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<ResponseEntity<Product>> getProductById(@PathVariable("id") Long id) {
//        return productService.findById(id)
//                .map(product -> ResponseEntity.ok(product));
//    }
//
//    @PostMapping("/add")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Mono<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
//        return productService.save(productRequestDTO);
//    }
//
//    @PutMapping("/{id}/deduct")
//    @ResponseStatus(HttpStatus.OK)
//    public Mono<ProductResponseDTO> deductProduct(@PathVariable("id") Long id) {
//        return productService.deductProduct(id);
//    }

//}
