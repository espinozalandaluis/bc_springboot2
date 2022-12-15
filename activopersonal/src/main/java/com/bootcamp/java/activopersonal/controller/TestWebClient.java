package com.bootcamp.java.activopersonal.controller;

import com.bootcamp.java.activopersonal.dto.webclients.client.ClientResponseDTO;
import com.bootcamp.java.activopersonal.dto.webclients.product.ProductResponseDTO;
import com.bootcamp.java.activopersonal.service.webClients.client.WcClientsService;
import com.bootcamp.java.activopersonal.service.webClients.product.WcProductsService;
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
@RequestMapping("/v1/testWebClient")
public class TestWebClient {
    @Autowired
    private WcClientsService wcClientsService;

    @GetMapping("/GetAllClients")
    public Mono<ResponseEntity<Flux<ClientResponseDTO>>> getAllProducts(){
        log.info("getAllProducts executed");
        return Mono.just(ResponseEntity.ok()
                .body(wcClientsService.findAll()));
    }

    @GetMapping("/{documentNumber}")
    public Mono<ResponseEntity<ClientResponseDTO>> getProductById(@PathVariable String documentNumber){
        return wcClientsService.findById(documentNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
