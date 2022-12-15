package com.bootcamp.java.activoempresarial.service.webClients.client;

import com.bootcamp.java.activoempresarial.dto.webclients.client.ClientResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcClientsService {
    public Flux<ClientResponseDTO> findAll();

    public Mono<ClientResponseDTO> findById(String documentNumber);
}
