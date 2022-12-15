package com.bootcamp.java.controller;

import com.bootcamp.java.dto.ClientDTO;
import com.bootcamp.java.dto.ClientResponseDTO;
import com.bootcamp.java.service.client.IClientService;
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
@RequestMapping("/v1/client")
public class ClientController {
    @Autowired
    private IClientService cService;
    @GetMapping()
    public Mono<ResponseEntity<Flux<ClientResponseDTO>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok().body(cService.findAll()));
    }

    @GetMapping("/{documentNumber}")
    public Mono<ResponseEntity<ClientResponseDTO>> getByDocumentNumber(@PathVariable String documentNumber){
        log.info("getByDocumentNumber {}", documentNumber);
        return cService.findByDocumentNumber(documentNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ClientDTO>> create(@Valid @RequestBody ClientDTO clientDTO){
        log.info("create executed {}", clientDTO);
        return  cService.create(clientDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{idClient}")
    public Mono<ResponseEntity<ClientDTO>> updateByIdClient(@PathVariable Integer idClient, @Valid @RequestBody ClientDTO request){
        log.info("updateById executed {}:{}",idClient, request);
        return cService.update(idClient, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());

    }

    @DeleteMapping("/{idClient}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer idClient){
        log.info("deletingById executed {}",idClient);
        return cService.delete(idClient)
                .map(r -> ResponseEntity.ok().<Void>build());
        /*
        log.info("deletingById executed {}",idClient);
        return cService.delete(idClient)
                .map(r -> ResponseEntity.ok().<Void>build());

         */
    }
}
