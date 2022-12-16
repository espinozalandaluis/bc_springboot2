package com.bootcamp.java.activopersonal.service.transaction;

import com.bootcamp.java.activopersonal.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import com.bootcamp.java.activopersonal.model.TransactionRequestModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    public Flux<TransactionDTO> findAll();
    //public Flux<ProductClientTransactionDTO> findByDocumentNumber(String documentNumber);

    public Mono<TransactionDTO> register(TransactionRequestModel transactionRequestDTO);

    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO,
                                                          String IdProductClient);

    public Flux<ProductClientTransactionDTO> findByDocumentNumber(String documentNumber);

    //public Mono<ProductClientTransactionDTO> create(TransactionRequestModel transactionRequestModel);

}
