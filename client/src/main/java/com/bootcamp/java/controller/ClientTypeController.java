package com.bootcamp.java.controller;

import com.bootcamp.java.dto.ClientTypeDTO;
import com.bootcamp.java.service.clientType.IClientTypeService;
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
@RequestMapping("/v1/clientType")
public class ClientTypeController {
    @Autowired
    private IClientTypeService cService;
    @GetMapping()
    public Mono<ResponseEntity<Flux<ClientTypeDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(cService.findAll()));
    }

    @GetMapping("/{idClientType}")
    public Mono<ResponseEntity<ClientTypeDTO>> getByNumber(@PathVariable Integer idClientType){
        log.info("getByNumber {}", idClientType);
        return cService.findByIdClientType(idClientType)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
