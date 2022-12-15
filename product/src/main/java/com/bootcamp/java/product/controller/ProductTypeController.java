package com.bootcamp.java.product.controller;


import com.bootcamp.java.product.dto.ProductTypeDTO;
import com.bootcamp.java.product.service.productType.ProductTypeService;
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
@RequestMapping("/v1/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;


    @GetMapping()
    public Mono<ResponseEntity<Flux<ProductTypeDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(productTypeService.findAll()));
    }

    @GetMapping("/{idProductType}")
    public Mono<ResponseEntity<ProductTypeDTO>> getById(@PathVariable Integer idProductType){
        log.info("getById executed {}", idProductType);
        return productTypeService.findById(idProductType)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }


}
