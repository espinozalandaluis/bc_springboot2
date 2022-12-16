package com.bootcamp.java.activopersonal.repository;

import com.bootcamp.java.activopersonal.entity.ProductClientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductClientRepository extends ReactiveMongoRepository<ProductClientEntity,Integer> {

    Mono<ProductClientEntity> findById(String Id);

    Flux<ProductClientEntity> findByDocumentNumber(String DocumentNumber);
    Mono<ProductClientEntity> findByAccountNumber(String AccountNumber);

}