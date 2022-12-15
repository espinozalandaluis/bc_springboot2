package com.bootcamp.java.controller;

import com.bootcamp.java.dto.ClientDocumentTypeDTO;
import com.bootcamp.java.service.clientDocumentType.IClientDocumentTypeService;
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
@RequestMapping("/v1/clientDocumentType")
public class ClientDocumentTypeController {
    @Autowired
    private IClientDocumentTypeService cService;
    @GetMapping()
    public Mono<ResponseEntity<Flux<ClientDocumentTypeDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(cService.findAll()));
    }

    @GetMapping("/{idClientDocumentType}")
    public Mono<ResponseEntity<ClientDocumentTypeDTO>> getByDocumentNumber(@PathVariable Integer idClientDocumentType){
        log.info("getByDocumentNumber {}", idClientDocumentType);
        return cService.findByIdClientDocumentType(idClientDocumentType)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
