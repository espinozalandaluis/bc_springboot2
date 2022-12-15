package com.bootcamp.java.pasivoahorro.repository;

import com.bootcamp.java.pasivoahorro.entity.TransactionType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TransactionTypeRepository extends ReactiveMongoRepository<TransactionType, Integer> {
    Mono<TransactionType> findByIdTransactionType(Integer IdTransactionType);
}
