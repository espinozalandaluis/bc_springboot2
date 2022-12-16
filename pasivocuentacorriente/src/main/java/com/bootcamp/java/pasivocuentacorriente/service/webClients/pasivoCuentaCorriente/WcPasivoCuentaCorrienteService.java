package com.bootcamp.java.pasivocuentacorriente.service.webClients.pasivoCuentaCorriente;

import com.bootcamp.java.pasivocuentacorriente.dto.ProductClientDTO;
import com.bootcamp.java.pasivocuentacorriente.dto.TransactionDTO;
import reactor.core.publisher.Mono;

public interface WcPasivoCuentaCorrienteService {

    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber);

    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO,String IdProductClient);

}
