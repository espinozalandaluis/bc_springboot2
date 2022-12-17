package com.bootcamp.java.pasivoahorro.service.webClients.pasivoCuentaCorriente;

import com.bootcamp.java.pasivoahorro.common.Constantes;
import com.bootcamp.java.pasivoahorro.dto.ProductClientDTO;
import com.bootcamp.java.pasivoahorro.dto.TransactionDTO;
import com.bootcamp.java.pasivoahorro.dto.webClientDTO.ClientResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WcPasivoCuentaCorrienteServiceImpl implements WcPasivoCuentaCorrienteService {

    private final WebClient wcPasivoCuentaCorriente = WebClient.builder()
            .baseUrl(Constantes.WebClientUriMSPasivoCuentaCorriente)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    private final WebClient wcPasivoCuentaCorrienteTrx = WebClient.builder()
            .baseUrl(Constantes.WebClientUriMSPasivoCuentaCorrienteTrx)
            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();




    @Override
    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber) {

        log.info(wcPasivoCuentaCorriente.get()
                .uri("/{AccountNumber}" ,AccountNumber)
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToMono(ProductClientDTO.class)
                .timeout(Duration.ofMillis(Constantes.TimeOutWebClients)).toString());

        return wcPasivoCuentaCorriente.get()
                .uri("/{accountNumber}" ,AccountNumber)
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToMono(ProductClientDTO.class)
                .timeout(Duration.ofMillis(Constantes.TimeOutWebClients));
    }

    @Override
    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO,
                                                          String IdProductClient) {
        return wcPasivoCuentaCorrienteTrx.post()
                .uri("/{IdProductClient}" ,IdProductClient)
                .body(Mono.just(transactionDTO), TransactionDTO.class)
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToMono(TransactionDTO.class)
                .timeout(Duration.ofMillis(Constantes.TimeOutWebClients));
    }
}
