package com.bootcamp.java.product.repository;

import com.bootcamp.java.product.entity.ProductSubType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductSubTypeRepository extends ReactiveMongoRepository<ProductSubType, String> {
    Mono<ProductSubType> findByIdProductSubType(Integer IdProductSubType);
}
