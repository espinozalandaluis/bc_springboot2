package com.bootcamp.java.service.clientType;

import com.bootcamp.java.dto.ClientDTO;
import com.bootcamp.java.dto.ClientTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientTypeService {
    public Flux<ClientTypeDTO> findAll();
    public Mono<ClientTypeDTO> findByIdClientType(Integer IdClientType);
}
