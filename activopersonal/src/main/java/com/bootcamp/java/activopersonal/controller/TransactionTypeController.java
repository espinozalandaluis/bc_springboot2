package com.bootcamp.java.activopersonal.controller;

import com.bootcamp.java.activopersonal.dto.TransactionTypeDTO;
import com.bootcamp.java.activopersonal.service.transactionType.TransactionTypeService;
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
@RequestMapping("/v1/transactionType")
public class TransactionTypeController {

    @Autowired
    private TransactionTypeService transactionTypeService;


    @GetMapping()
    public Mono<ResponseEntity<Flux<TransactionTypeDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(transactionTypeService.findAll()));
    }

    @GetMapping("/{idTransactionType}")
    public Mono<ResponseEntity<TransactionTypeDTO>> getById(@PathVariable Integer idTransactionType){
        log.info("getById executed {}", idTransactionType);
        return transactionTypeService.findById(idTransactionType)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
