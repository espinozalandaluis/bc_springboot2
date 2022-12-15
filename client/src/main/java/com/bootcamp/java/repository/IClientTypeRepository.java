package com.bootcamp.java.repository;

import com.bootcamp.java.entity.ClientEntity;
import com.bootcamp.java.entity.ClientTypeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IClientTypeRepository extends ReactiveMongoRepository<ClientTypeEntity, String> {
    Mono<ClientTypeEntity> findByIdClientType(Integer idClientType);
}
