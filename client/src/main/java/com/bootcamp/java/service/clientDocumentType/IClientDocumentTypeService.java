package com.bootcamp.java.service.clientDocumentType;

import com.bootcamp.java.dto.ClientDocumentTypeDTO;
import com.bootcamp.java.dto.ClientTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientDocumentTypeService {
    public Flux<ClientDocumentTypeDTO> findAll();
    public Mono<ClientDocumentTypeDTO> findByIdClientDocumentType(Integer IdClientDocumentType);
}
