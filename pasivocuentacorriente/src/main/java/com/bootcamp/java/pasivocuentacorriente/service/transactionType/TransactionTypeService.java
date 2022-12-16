package com.bootcamp.java.pasivocuentacorriente.service.transactionType;

import com.bootcamp.java.pasivocuentacorriente.dto.TransactionTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionTypeService {
    public Flux<TransactionTypeDTO> findAll();

    public Mono<TransactionTypeDTO> findById(Integer IdTransactionType);
}
