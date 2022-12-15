package com.bootcamp.java.pasivoahorro.service.webClients.Products;

import com.bootcamp.java.pasivoahorro.dto.webClientDTO.ProductResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcProductsService {

    public Flux<ProductResponseDTO> findAll();

    public Mono<ProductResponseDTO> findById(Integer IdProduct);

}
