package com.bootcamp.java.activopersonal.service.productClient;

import com.bootcamp.java.activopersonal.dto.ProductClientDTO;
import com.bootcamp.java.activopersonal.dto.ProductClientRequest;
import com.bootcamp.java.activopersonal.dto.ProductClientTransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductClientService {

    public Flux<ProductClientDTO> findAll();

    public Flux<ProductClientDTO> findByDocumentNumber(String DocumentNumber);

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<ProductClientTransactionDTO> create(ProductClientRequest productClientRequest);

}
