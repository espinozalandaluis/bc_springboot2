package com.bootcamp.java.activoempresarial.service.transactionType;

import com.bootcamp.java.activoempresarial.dto.TransactionTypeDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionTypeService {
    public Flux<TransactionTypeDTO> findAll();

    public Mono<TransactionTypeDTO> findById(Integer IdTransactionType);
}
