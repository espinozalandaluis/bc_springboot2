package com.bootcamp.java.activoempresarial.repository;

import com.bootcamp.java.activoempresarial.entity.ProductClientEntity;
import com.bootcamp.java.activoempresarial.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<TransactionEntity,Integer> {

    Mono<TransactionEntity> findById(String Id);

}
