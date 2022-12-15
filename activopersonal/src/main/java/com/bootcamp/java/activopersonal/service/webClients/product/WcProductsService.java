package com.bootcamp.java.activopersonal.service.webClients.product;

import com.bootcamp.java.activopersonal.dto.webclients.product.ProductResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcProductsService {

    public Flux<ProductResponseDTO> findAll();

    public Mono<ProductResponseDTO> findById(Integer IdProduct);

}
