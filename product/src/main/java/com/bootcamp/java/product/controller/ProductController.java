package com.bootcamp.java.product.controller;

import com.bootcamp.java.product.dto.ProductDTO;
import com.bootcamp.java.product.dto.ProductResponseDTO;
import com.bootcamp.java.product.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/product")
public class ProductController {
/*
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;
*/


    @Autowired
    private ProductService productService;

    @GetMapping()
    public Mono<ResponseEntity<Flux<ProductResponseDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(productService.findAll()));
    }

    @GetMapping("/{idProduct}")
    public Mono<ResponseEntity<ProductResponseDTO>> getById(@PathVariable Integer idProduct){
        log.info("getById executed {}", idProduct);
        return productService.findById(idProduct)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductResponseDTO>> create(@Valid @RequestBody ProductDTO request) {
        log.info("create executed {}", request);
        return productService.create(request)
               .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{idProduct}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer idProduct){
        log.info("deleteById executed {}", idProduct);
        return productService.delete(idProduct)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PutMapping
    public Mono<ResponseEntity<ProductResponseDTO>> updateByIdProduct(@Valid @RequestBody ProductDTO request){
        log.info("updateById executed {}", request);
        return productService.update(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }


}
