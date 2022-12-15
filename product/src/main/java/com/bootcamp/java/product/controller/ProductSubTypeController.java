package com.bootcamp.java.product.controller;

import com.bootcamp.java.product.dto.ProductSubTypeDTO;
import com.bootcamp.java.product.service.productSubType.ProductSubTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/productSubType")
public class ProductSubTypeController {
    @Autowired
    private ProductSubTypeService productSubType;

    @GetMapping()
    public Mono<ResponseEntity<Flux<ProductSubTypeDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(productSubType.findAll()));
    }

    @GetMapping("/{idProductSubType}")
    public Mono<ResponseEntity<ProductSubTypeDTO>> getById(@PathVariable Integer idProductSubType){
        log.info("getById executed {}", idProductSubType);
        return productSubType.findById(idProductSubType)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
