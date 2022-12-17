package com.bootcamp.java.activopersonal.controller;

import com.bootcamp.java.activopersonal.dto.ProductClientDTO;
import com.bootcamp.java.activopersonal.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import com.bootcamp.java.activopersonal.model.MembershipRequestModel;
import com.bootcamp.java.activopersonal.service.productClient.ProductClientService;
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
@RequestMapping("/v1/afiliacionActivoPersonal")
public class ProductClientController {

    @Autowired
    private ProductClientService productClientService;

    @GetMapping()
    public Mono<ResponseEntity<Flux<ProductClientDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(productClientService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductClientDTO>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        return productClientService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductClientTransactionDTO>> create(@Valid @RequestBody MembershipRequestModel membershipRequestModel){
        log.info("create excecuted {}",membershipRequestModel);
        return productClientService.create(membershipRequestModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
