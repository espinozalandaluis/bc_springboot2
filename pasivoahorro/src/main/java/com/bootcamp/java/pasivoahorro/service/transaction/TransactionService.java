package com.bootcamp.java.pasivoahorro.service.transaction;

import com.bootcamp.java.pasivoahorro.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    public Mono<TransactionDTO> registerTrx(TransactionDTO transactionDTO);

    public Mono<TransactionDTO> registerTrx2(TransactionRequestDTO transactionRequestDTO);

    public Flux<ProductClientTransactionDTO2> findByDocumentNumber(String documentNumber);

    public Flux<TransactionDTO> findAll();

}
