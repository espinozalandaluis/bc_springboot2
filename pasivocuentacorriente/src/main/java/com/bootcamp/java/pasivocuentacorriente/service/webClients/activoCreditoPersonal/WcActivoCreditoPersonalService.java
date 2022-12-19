package com.bootcamp.java.pasivocuentacorriente.service.webClients.activoCreditoPersonal;

import com.bootcamp.java.pasivocuentacorriente.dto.ProductClientDTO;
import com.bootcamp.java.pasivocuentacorriente.dto.TransactionDTO;
import reactor.core.publisher.Mono;

public interface WcActivoCreditoPersonalService {

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO, String IdProductClient);


}
