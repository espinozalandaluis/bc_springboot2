package com.bootcamp.java.activopersonal.repository;

import com.bootcamp.java.activopersonal.entity.ProductClientEntity;
import com.bootcamp.java.activopersonal.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<TransactionEntity,Integer> {

    Mono<TransactionEntity> findById(String Id);

    Flux<TransactionEntity> findByIdProductClient(String IdProductClient);

    @Query("{ 'registrationDate' : { $gte: ?0}, 'idProductClient' : ?1 }")
    Flux<TransactionEntity> findTrxPerMoth(Date FechaDesde, String IdProductClient);

}
