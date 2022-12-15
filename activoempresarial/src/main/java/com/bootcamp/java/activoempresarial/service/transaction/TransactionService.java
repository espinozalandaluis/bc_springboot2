package com.bootcamp.java.activoempresarial.service.transaction;

import com.bootcamp.java.activoempresarial.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activoempresarial.dto.TransactionDTO;
import com.bootcamp.java.activoempresarial.model.TransactionRequestModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

    public Flux<TransactionDTO> findAll();

    public Mono<TransactionDTO> findById(String Id);

    public Mono<ProductClientTransactionDTO> create(TransactionRequestModel transactionRequestModel);

}
