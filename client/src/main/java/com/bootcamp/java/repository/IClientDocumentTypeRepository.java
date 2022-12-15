package com.bootcamp.java.repository;

import com.bootcamp.java.entity.ClientDocumentTypeEntity;
import com.bootcamp.java.entity.ClientTypeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IClientDocumentTypeRepository extends ReactiveMongoRepository<ClientDocumentTypeEntity, String> {
    Mono<ClientDocumentTypeEntity> findByIdClientDocumentType(Integer idClientDocumentType);
}
