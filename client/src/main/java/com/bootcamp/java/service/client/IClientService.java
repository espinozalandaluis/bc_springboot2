package com.bootcamp.java.service.client;

import com.bootcamp.java.dto.ClientResponseDTO;
import com.bootcamp.java.entity.ClientEntity;
import com.bootcamp.java.dto.ClientDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {
    public Flux<ClientResponseDTO> findAll();
    public Mono<ClientResponseDTO> findByDocumentNumber(String documentNumber);
    public Mono<ClientDTO> findByIdClient(Integer idClient);
    public Mono<ClientDTO> create(ClientDTO clientDTO);
    public Mono<ClientDTO> update(Integer idClient,ClientDTO clientDTO);
    public Mono<ClientDTO> delete(Integer idClient);


}
