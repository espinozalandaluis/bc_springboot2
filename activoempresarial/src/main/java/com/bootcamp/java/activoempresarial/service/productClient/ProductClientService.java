package com.bootcamp.java.activoempresarial.service.productClient;

import com.bootcamp.java.activoempresarial.dto.ProductClientDTO;
import com.bootcamp.java.activoempresarial.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activoempresarial.dto.TransactionDTO;
import com.bootcamp.java.activoempresarial.model.MembershipRequestModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductClientService {
    public Flux<ProductClientDTO> findAll();

    public Mono<ProductClientDTO> findById(String Id);

    public Mono<ProductClientTransactionDTO> create(MembershipRequestModel membershipRequestModel);

}
