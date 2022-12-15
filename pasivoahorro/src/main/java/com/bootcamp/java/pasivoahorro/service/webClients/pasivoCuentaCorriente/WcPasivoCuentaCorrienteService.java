package com.bootcamp.java.pasivoahorro.service.webClients.pasivoCuentaCorriente;

import com.bootcamp.java.pasivoahorro.dto.ProductClientDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WcPasivoCuentaCorrienteService {
    public Mono<ProductClientDTO> findByDocumentNumber(String DocumentNumber);
}
