package com.bootcamp.java.pasivoahorro.service.webClients.pasivoPlazoFijo;

import com.bootcamp.java.pasivoahorro.dto.ProductClientDTO;
import com.bootcamp.java.pasivoahorro.dto.TransactionDTO;
import reactor.core.publisher.Mono;

public interface WcPasivoPlazoFijoService {

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO, String IdProductClient);

}
