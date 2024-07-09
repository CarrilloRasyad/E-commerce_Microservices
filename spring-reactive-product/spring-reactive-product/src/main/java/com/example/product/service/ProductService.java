package com.example.product.service;


import com.example.common.dto.OrderItemDTO;
import com.example.common.dto.OrdersDTO;
import com.example.common.status.OrdersStatus;
import com.example.product.dto.ProductRequestDTO;
import com.example.product.dto.ProductResponseDTO;
import com.example.product.exception.ProductNotFoundException;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository repo;

    public Flux<Product> findAll() {
        return repo.findAll();
    }

    public Mono<Product> findById(Long id) {
        return repo.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(String.format("Product not found. ID: %s", id))));
    }

    public Mono<ProductResponseDTO> save(ProductRequestDTO productRequestDTO) {
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .price(productRequestDTO.getPrice())
                .category(productRequestDTO.getCategory())
                .description(productRequestDTO.getDescription())
                .imageUrl(productRequestDTO.getImageUrl())
                .stockQuantity(productRequestDTO.getStockQuantity())
                .build();

        return repo.save(product)
                .map(savedProduct -> ProductResponseDTO.builder()
                        .id(savedProduct.getId())
                        .name(savedProduct.getName())
                        .price(savedProduct.getPrice())
                        .category(savedProduct.getCategory())
                        .description(savedProduct.getDescription())
                        .imageUrl(savedProduct.getImageUrl())
                        .stockQuantity(savedProduct.getStockQuantity())
                        .createdAt(savedProduct.getCreatedAt())
                        .updatedAt(savedProduct.getUpdatedAt())
                        .build());
    }
//    public Mono<OrdersDTO> deductProduct(OrdersDTO ordersDTO) {
//        if (ordersDTO.getOrderItemDTO().isEmpty()) {
//            ordersDTO.setStatus(OrdersStatus.FAILED);
//            return Mono.just(ordersDTO);
//        }
//
//        OrderItemDTO orderItem = ordersDTO.getOrderItemDTO().get(0);
//        return repo.findById(orderItem.getProductId())
//                .flatMap(product -> {
//                    if (product.getStockQuantity() >= orderItem.getQuantity()) {
//                        product.setStockQuantity(product.getStockQuantity() - orderItem.getQuantity());
//                        return repo.save(product)
//                                .thenReturn(ordersDTO)
//                                .doOnNext(dto -> {
//                                    dto.setStatus(OrdersStatus.CREATED);
//                                    logger.info("Stock deducted successfully for product ID: {}", orderItem.getProductId());
//                                });
//                    } else {
//                        ordersDTO.setStatus(OrdersStatus.FAILED);
//                        logger.warn("Insufficient stock for product ID: {}", orderItem.getProductId());
//                        return Mono.just(ordersDTO);
//                    }
//                })
//                .defaultIfEmpty(ordersDTO)
//                .doOnNext(dto -> {
//                    if (dto.getStatus() != OrdersStatus.CREATED) {
//                        dto.setStatus(OrdersStatus.FAILED);
//                        logger.warn("Product not found for ID: {}", orderItem.getProductId());
//                    }
//                });
//    }

//    public Mono<OrdersDTO> deductProduct(OrdersDTO orderDTO) {
//        Integer quantity = orderDTO.getOrderItemDTO().get(0).getQuantity();
//        return repo.findById(orderDTO.getOrderItemDTO().get(0).getProductId())
//                .flatMap(product -> {
//                    if (product.getStockQuantity() >= quantity) {
//                        product.setStockQuantity(product.getStockQuantity() - quantity);
//                        orderDTO.setStatus(OrdersStatus.CREATED);
//                        return repo.save(product).thenReturn(orderDTO);
//                    } else {
//                        orderDTO.setStatus(OrdersStatus.FAILED);
//                        return Mono.just(orderDTO);
//                    }
//                });
//    }
public Mono<OrdersDTO> deductProduct(OrdersDTO orderDTO) {
    logger.info("ini didalem product deduct" + orderDTO);
//    var id = orderDTO.getId();
    if (orderDTO.getOrderItemDTO() == null || orderDTO.getOrderItemDTO().isEmpty()) {
        return Mono.error(new IllegalArgumentException("Order items cannot be null or empty"));
    }

    Integer quantity = orderDTO.getOrderItemDTO().get(0).getQuantity();
    return repo.findById(orderDTO.getOrderItemDTO().get(0).getProductId())
            .flatMap(product -> {
                if (product.getStockQuantity() >= quantity) {
                    product.setStockQuantity(product.getStockQuantity() - quantity);
                    orderDTO.setStatus(OrdersStatus.CREATED);
                    return repo.save(product).thenReturn(orderDTO);
                } else {
                    orderDTO.setStatus(OrdersStatus.FAILED);
                    return Mono.just(orderDTO);
                }
            });
}
}

//    public Mono<ProductResponseDTO> deductProduct(Long id) {
//        return repo.findById(id)
//                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
//                .flatMap(product -> {
//                    if (product.getStockQuantity() > 0) {
//                        product.setStockQuantity(product.getStockQuantity() - 1);
//                        return repo.save(product)
//                                .doOnSuccess(savedProduct ->
//                                        logger.info("Product with id {} deducted from stock", id)
//                                );
//                    } else {
//                        logger.info("Product with id {} is out of stock", id);
//                        return Mono.error(new RuntimeException("Product out of stock"));
//                    }
//                })
//                .map(updatedProduct -> ProductResponseDTO.builder()
//                        .id(updatedProduct.getId())
//                        .name(updatedProduct.getName())
//                        .price(updatedProduct.getPrice())
//                        .category(updatedProduct.getCategory())
//                        .description(updatedProduct.getDescription())
//                        .imageUrl(updatedProduct.getImageUrl())
//                        .stockQuantity(updatedProduct.getStockQuantity())
//                        .createdAt(updatedProduct.getCreatedAt())
//                        .updatedAt(updatedProduct.getUpdatedAt())
//                        .build());
//    }

    //    public Flux<Product> findAll() {
//        return repo.findAll();
//    }
//    public Mono<Product> findById(Long id) {
//        return repo.findById(id)
//                .switchIfEmpty(Mono.error(new ProductNotFoundException(String.format("Product not found. ID: %s", id))));
//    }
//    public Mono<Product> save(Product product) {
//        return repo.save(product);
//    }

//    public Mono<Product> deductProduct(Long id) {
//        return repo.findById(id)
//                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
//                .flatMap(product -> {
//                    if (product.getStockQuantity() > 0) {
//                        product.setStockQuantity(product.getStockQuantity() - 1);
//                        return repo.save(product)
//                                .doOnSuccess(savedProduct ->
//                                        logger.info("Product with id {} deducted from stock", id)
//                                );
//                    } else {
//                        logger.info("Product with id {} is out of stock", id);
//                        return Mono.error(new RuntimeException("Product out of stock"));
//                    }
//                });
//    }
//}
