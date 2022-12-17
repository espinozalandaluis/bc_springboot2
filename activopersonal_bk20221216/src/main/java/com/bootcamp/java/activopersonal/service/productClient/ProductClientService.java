package com.bootcamp.java.activopersonal.service.productClient;

import com.bootcamp.java.activopersonal.dto.ProductClientDTO;
import com.bootcamp.java.activopersonal.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import com.bootcamp.java.activopersonal.model.MembershipRequestModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductClientService {
    public Flux<ProductClientDTO> findAll();

    public Mono<ProductClientDTO> findById(String Id);

    public Mono<ProductClientTransactionDTO> create(MembershipRequestModel membershipRequestModel);

}
