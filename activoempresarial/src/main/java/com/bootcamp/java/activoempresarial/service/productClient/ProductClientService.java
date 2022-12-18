package com.bootcamp.java.activoempresarial.service.productClient;

import com.bootcamp.java.activoempresarial.dto.ProductClientDTO;
import com.bootcamp.java.activoempresarial.dto.ProductClientRequest;
import com.bootcamp.java.activoempresarial.dto.ProductClientTransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductClientService {

    public Flux<ProductClientDTO> findAll();

    public Flux<ProductClientDTO> findByDocumentNumber(String DocumentNumber);

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<ProductClientTransactionDTO> create(ProductClientRequest productClientRequest);

}
