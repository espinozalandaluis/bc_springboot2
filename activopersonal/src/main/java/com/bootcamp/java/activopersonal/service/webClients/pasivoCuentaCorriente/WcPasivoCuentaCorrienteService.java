package com.bootcamp.java.activopersonal.service.webClients.pasivoCuentaCorriente;

import com.bootcamp.java.activopersonal.dto.ProductClientDTO;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcPasivoCuentaCorrienteService {

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO,String IdProductClient);

}
