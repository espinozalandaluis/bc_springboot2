package com.bootcamp.java.pasivoahorro.service.webClients.activoTarjetaCredito;

import com.bootcamp.java.pasivoahorro.dto.ProductClientDTO;
import com.bootcamp.java.pasivoahorro.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcActivoTarjetaCreditoService {

    public Flux<ProductClientDTO> findByDocumentNumber(String DocumentNumber);


    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO, String IdProductClient);


}
