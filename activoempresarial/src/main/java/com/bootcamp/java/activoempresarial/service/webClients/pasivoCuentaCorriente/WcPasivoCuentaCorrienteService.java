package com.bootcamp.java.activoempresarial.service.webClients.pasivoCuentaCorriente;

import com.bootcamp.java.activoempresarial.dto.ProductClientDTO;
import com.bootcamp.java.activoempresarial.dto.TransactionDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcPasivoCuentaCorrienteService {

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO,String IdProductClient);

}
