package com.bootcamp.java.product.service.productType;

import com.bootcamp.java.product.dto.ProductTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductTypeService {


    public Flux<ProductTypeDTO> findAll();

    public Mono<ProductTypeDTO> findById(Integer idProductType);

}
