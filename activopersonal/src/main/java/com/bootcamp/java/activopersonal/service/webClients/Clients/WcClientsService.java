package com.bootcamp.java.activopersonal.service.webClients.Clients;

import com.bootcamp.java.activopersonal.dto.webClientDTO.ClientResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcClientsService {

    public Flux<ClientResponseDTO> findAll();
    public Mono<ClientResponseDTO> findById(Integer IdClient);

    public Mono<ClientResponseDTO> findByDocumentNumber(String documentNumber);
}
