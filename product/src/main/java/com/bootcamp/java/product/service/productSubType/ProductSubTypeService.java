package com.bootcamp.java.product.service.productSubType;


import com.bootcamp.java.product.dto.ProductSubTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductSubTypeService {


    public Flux<ProductSubTypeDTO> findAll();

    public Mono<ProductSubTypeDTO> findById(Integer idProductSubType);

}
