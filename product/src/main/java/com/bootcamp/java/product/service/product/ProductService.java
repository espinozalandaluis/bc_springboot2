package com.bootcamp.java.product.service.product;

import com.bootcamp.java.product.dto.ProductDTO;
import com.bootcamp.java.product.dto.ProductResponseDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    //public Flux<ProductDTO> findAll();
    public Flux<ProductResponseDTO> findAll();

    //public Mono<ProductDTO> findById(Integer idProduct);
    public Mono<ProductResponseDTO> findById(Integer idProduct);

    public Mono<ProductResponseDTO> create(ProductDTO productDTO);

    public Mono<ProductResponseDTO> update(ProductDTO productDTO);

    public Mono<ProductDTO> delete(Integer idProduct);

}
