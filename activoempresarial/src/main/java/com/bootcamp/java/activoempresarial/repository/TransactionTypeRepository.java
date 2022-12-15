package com.bootcamp.java.activoempresarial.repository;

import com.bootcamp.java.activoempresarial.entity.TransactionTypeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionTypeRepository extends ReactiveMongoRepository<TransactionTypeEntity, Integer> {

    Mono<TransactionTypeEntity> findByIdTransactionType(Integer IdTransactionType);

}
