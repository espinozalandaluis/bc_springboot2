package com.bootcamp.java.activopersonal.controller;

import com.bootcamp.java.activopersonal.dto.ProductClientDTO;
import com.bootcamp.java.activopersonal.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import com.bootcamp.java.activopersonal.model.MembershipRequestModel;
import com.bootcamp.java.activopersonal.model.TransactionRequestModel;
import com.bootcamp.java.activopersonal.service.transaction.TransactionService;
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
@RequestMapping("/v1/transactionActivoPersonal")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @GetMapping()
    public Mono<ResponseEntity<Flux<TransactionDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(transactionService.findAll()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TransactionDTO>> getById(@PathVariable String id){
        log.info("getById executed {}", id);
        return transactionService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductClientTransactionDTO>> create(@Valid @RequestBody TransactionRequestModel transactionRequestModel){
        log.info("create excecuted {}",transactionRequestModel);
        return transactionService.create(transactionRequestModel)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
