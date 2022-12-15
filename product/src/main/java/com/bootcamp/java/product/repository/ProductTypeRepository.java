package com.bootcamp.java.product.repository;

import com.bootcamp.java.product.entity.ProductType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductTypeRepository extends ReactiveMongoRepository<ProductType, Integer> {

    Mono<ProductType> findByIdProductType(Integer IdProductType);


}
