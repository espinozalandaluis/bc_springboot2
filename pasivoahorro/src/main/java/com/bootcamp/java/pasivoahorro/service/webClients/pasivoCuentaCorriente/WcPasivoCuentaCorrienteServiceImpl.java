package com.bootcamp.java.pasivoahorro.service.webClients.pasivoCuentaCorriente;

import com.bootcamp.java.pasivoahorro.common.Constantes;
import com.bootcamp.java.pasivoahorro.dto.ProductClientDTO;
import com.bootcamp.java.pasivoahorro.dto.webClientDTO.ClientResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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
    public Mono<ProductClientDTO> findByDocumentNumber(String DocumentNumber) {
        return wcPasivoCuentaCorriente.get()
                .uri("/{documentNumber}" ,DocumentNumber)
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToMono(ProductClientDTO.class)
                .timeout(Duration.ofMillis(10_000));
    }
}
