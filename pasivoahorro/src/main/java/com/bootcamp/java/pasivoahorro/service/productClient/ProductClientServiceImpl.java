package com.bootcamp.java.pasivoahorro.service.productClient;

import com.bootcamp.java.pasivoahorro.common.Constantes;
import com.bootcamp.java.pasivoahorro.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.pasivoahorro.converter.ProductClientConvert;
import com.bootcamp.java.pasivoahorro.converter.TransactionConvert;
import com.bootcamp.java.pasivoahorro.dto.ProductClientDTO;
import com.bootcamp.java.pasivoahorro.dto.ProductClientRequest;
import com.bootcamp.java.pasivoahorro.dto.ProductClientTransactionDTO;
import com.bootcamp.java.pasivoahorro.entity.ProductClient;
import com.bootcamp.java.pasivoahorro.entity.Transaction;
import com.bootcamp.java.pasivoahorro.repository.ProductClientRepository;
import com.bootcamp.java.pasivoahorro.repository.TransactionRepository;
import com.bootcamp.java.pasivoahorro.service.transaction.TransactionService;
import com.bootcamp.java.pasivoahorro.service.webClients.Clients.WcClientsService;
import com.bootcamp.java.pasivoahorro.service.webClients.Products.WcProductsService;
import com.bootcamp.java.pasivoahorro.service.webClients.activoTarjetaCredito.WcActivoTarjetaCreditoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductClientServiceImpl implements ProductClientService{

    @Autowired
    private ProductClientRepository productClientRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionConvert transactionConvert;

    @Autowired
    ProductClientConvert productClientConvert;

    @Autowired
    WcClientsService wcClientsService;

    @Autowired
    WcProductsService wcProductsService;

    @Autowired
    WcActivoTarjetaCreditoService wcActivoTarjetaCreditoService;

    @Override
    public Flux<ProductClientDTO> findAll() {
        log.debug("findAll executing");
        Flux<ProductClientDTO> dataProductClientDTO = productClientRepository.findAll()
                .map(ProductClientConvert::EntityToDTO);
        return dataProductClientDTO;
    }

    @Override
    public Flux<ProductClientDTO> findByDocumentNumber(String DocumentNumber) {
        log.debug("findByDocumentNumber executing");
        Flux<ProductClientDTO> dataProductClientDTO = productClientRepository.findByDocumentNumber(DocumentNumber)
                .map(ProductClientConvert::EntityToDTO)
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros")));;
        return dataProductClientDTO;
    }

    @Override
    public Mono<ProductClientDTO> findByAccountNumber(String AccountNumber) {
        return productClientRepository.findByAccountNumber(AccountNumber)
                .map(ProductClientConvert::EntityToDTO)
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros")));
    }

    @Override
    public Mono<ProductClientTransactionDTO> create(ProductClientRequest productClientRequest) {
        log.info("Procedimiento para crear una nueva afiliacion");
        log.info("======================>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(productClientRequest.toString());

        return productClientRepository.findByDocumentNumber(productClientRequest.getDocumentNumber()).collectList()
                .flatMap(valProdCli ->{
                    if (valProdCli.stream().count() == 0){
                        return wcClientsService.findByDocumentNumber(productClientRequest.getDocumentNumber())
                                .flatMap(cliente->{
                                    log.info("Resultado de llamar al servicio de Clients: {}",cliente.toString());
                                    if(!cliente.getClientTypeDTO().getIdClientType().equals(Constantes.ClientTypePersonal))
                                        return Mono.error(new FunctionalException("El cliente no es tipo Personal"));

                                    //Validar si tiene tarjeta de credito en caso de ser Personal VIP
                                    if(cliente.getClientTypeDTO().getIdClientType().equals(Constantes.ClientTypePersonalVIP))
                                    {
                                        return Mono.error(new FunctionalException("Falta implementar la validacion por al menos una tarjeta de credito"));
                                    }

                                    return wcProductsService.findById(productClientRequest.getIdProduct())
                                            .flatMap(producto->{
                                                log.info("Resultado de llamar al servicio de Products: {}",producto.toString());

                                                if(!producto.getProductTypeDTO().getIdProductType().equals(Constantes.ProductTypePasivo))
                                                    return Mono.error(new FunctionalException("El producto no es Tipo Pasivo"));
                                                if(!producto.getProductSubTypeDTO().getIdProductSubType().equals(Constantes.ProductSubTypePasivo))
                                                    return Mono.error(new FunctionalException("El producto no es SubTipo Ahorro"));

                                                return productClientRepository.findByAccountNumber(productClientRequest.getAccountNumber()).flux().collectList()
                                                        .flatMap(y->{
                                                            if(y.stream().count() > 0)
                                                                return Mono.error(new FunctionalException("Existe un AccountNumber"));

                                                            ProductClient prdCli = productClientConvert.DTOToEntity2(productClientRequest,
                                                                    producto,cliente);

                                                            return productClientRepository.save(prdCli)
                                                                    .flatMap(productocliente -> {
                                                                        log.info("Resultado de guardar ProductClient: {}",productocliente.toString());
                                                                        if(productClientRequest.getDepositAmount() > 0){
                                                                            //prdCli.setTransactionFee(0.00); //Por primera transaccion no se cobra Comision
                                                                            Transaction trxEntity = transactionConvert.ProductClientEntityToTransactionEntity(prdCli);
                                                                            log.info("before save transaction trxEntity: {}",trxEntity.toString());
                                                                            trxEntity.setTransactionFee(0.0);
                                                                            return transactionRepository.save(trxEntity)
                                                                                    .flatMap(trx -> {

                                                                                        log.info("Resultado de guardar Transactions: {}",trx.toString());
                                                                                        return Mono.just(ProductClientTransactionDTO.builder()
                                                                                                .productClientDTO(productClientConvert.EntityToDTO(productocliente))
                                                                                                .transactionDTO(transactionConvert.EntityToDTO(trx))
                                                                                                .build());
                                                                                    })
                                                                                    .switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al guardar el Transaction")));

                                                                        }
                                                                        else {
                                                                            return Mono.just(ProductClientTransactionDTO.builder()
                                                                                    .productClientDTO(productClientConvert.EntityToDTO(productocliente))
                                                                                    .build());
                                                                        }
                                                                    })
                                                                    .switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al guardar el ProductClient")));
                                                        });




                                            })
                                            .switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al consultar el servicio de producto")));
                                })
                                .switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al consultar el servicio de cliente")));
                        //return Mono.error(new FunctionalException(String.format("Ya existe una afiliacion asociada con el DocumentNumber: %s",productClientRequest.getDocumentNumber())));
                    }
                    else{
                        return Mono.error(new FunctionalException(String.format("Ya existe una afiliacion asociada con el DocumentNumber: %s",productClientRequest.getDocumentNumber())));
                    }
                });
    }
}
