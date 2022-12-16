package com.bootcamp.java.activopersonal.service.transactionType;

import com.bootcamp.java.activopersonal.dto.TransactionTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionTypeService {
    public Flux<TransactionTypeDTO> findAll();

    public Mono<TransactionTypeDTO> findById(Integer IdTransactionType);
}
