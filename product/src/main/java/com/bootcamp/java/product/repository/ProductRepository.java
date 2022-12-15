package com.bootcamp.java.product.repository;

import com.bootcamp.java.product.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Mono<Product> findByIdProduct(Integer IdProduct);

}
