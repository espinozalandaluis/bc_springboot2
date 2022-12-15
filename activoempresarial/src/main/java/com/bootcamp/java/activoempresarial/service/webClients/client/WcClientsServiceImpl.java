package com.bootcamp.java.activoempresarial.service.webClients.client;

import com.bootcamp.java.activoempresarial.dto.webclients.client.ClientResponseDTO;
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
public class WcClientsServiceImpl implements  WcClientsService {
    @Override
    public Flux<ClientResponseDTO> findAll() {
        WebClient client2 = WebClient.builder()
                .baseUrl("http://localhost:8082/v1/client")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client2.get()
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToFlux(ClientResponseDTO.class)
                .timeout(Duration.ofMillis(10_000));
    }

    @Override
    public Mono<ClientResponseDTO> findById(String documentNumber) {
        log.info("El numero de documento consultado es: {}",documentNumber);
        WebClient client2 = WebClient.builder()
                .baseUrl("http://localhost:8082/v1/client/" + documentNumber)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client2.get()
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToMono(ClientResponseDTO.class)
                .timeout(Duration.ofMillis(10_000));
    }
}