package com.bootcamp.java.repository;

import com.bootcamp.java.entity.ClientEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IClientRepository extends ReactiveMongoRepository<ClientEntity, String> {
    Mono<ClientEntity> findByDocumentNumber(String documentNumber);
    Mono<ClientEntity> findByIdClient(Integer idClient);
}
