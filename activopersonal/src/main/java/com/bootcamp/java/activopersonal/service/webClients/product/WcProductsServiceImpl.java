package com.bootcamp.java.activopersonal.service.webClients.product;

import com.bootcamp.java.activopersonal.dto.webclients.product.ProductResponseDTO;
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
public class WcProductsServiceImpl implements  WcProductsService{


    @Override
    public Flux<ProductResponseDTO> findAll() {
        WebClient client2 = WebClient.builder()
                .baseUrl("http://localhost:8081/v1/product")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        Flux<ProductResponseDTO> dataProductResponseDTO =  client2.get()
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToFlux(ProductResponseDTO.class);

        log.info("dataProductResponseDTO: {}",dataProductResponseDTO);

        return client2.get()
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToFlux(ProductResponseDTO.class)
                .timeout(Duration.ofMillis(10_000));


    }

    @Override
    public Mono<ProductResponseDTO> findById(Integer IdProduct) {
        log.info("El idProducto consultado es: {}",IdProduct.toString());
        WebClient client2 = WebClient.builder()
                .baseUrl("http://localhost:8081/v1/product/"+ IdProduct)
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();



        return client2.get()
                .retrieve()
                .onStatus(httpStatus -> HttpStatus.NO_CONTENT.equals(httpStatus),
                        response -> response.bodyToMono(String.class)
                                .map(Exception::new))
                .bodyToMono(ProductResponseDTO.class)
                .timeout(Duration.ofMillis(10_000));
    }
}