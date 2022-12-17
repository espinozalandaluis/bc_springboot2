package com.bootcamp.java.pasivocuentacorriente.service.productClient;

import com.bootcamp.java.pasivocuentacorriente.common.Constantes;
import com.bootcamp.java.pasivocuentacorriente.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.pasivocuentacorriente.converter.ProductClientConvert;
import com.bootcamp.java.pasivocuentacorriente.converter.TransactionConvert;
import com.bootcamp.java.pasivocuentacorriente.dto.ProductClientDTO;
import com.bootcamp.java.pasivocuentacorriente.dto.ProductClientRequest;
import com.bootcamp.java.pasivocuentacorriente.dto.ProductClientTransactionDTO;
import com.bootcamp.java.pasivocuentacorriente.entity.ProductClient;
import com.bootcamp.java.pasivocuentacorriente.entity.Transaction;
import com.bootcamp.java.pasivocuentacorriente.repository.ProductClientRepository;
import com.bootcamp.java.pasivocuentacorriente.repository.TransactionRepository;
import com.bootcamp.java.pasivocuentacorriente.service.transaction.TransactionService;
import com.bootcamp.java.pasivocuentacorriente.service.webClients.Clients.WcClientsService;
import com.bootcamp.java.pasivocuentacorriente.service.webClients.Products.WcProductsService;
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
        log.info("findByAccountNumber {}", AccountNumber);
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
                        return wcClientsService.findByDocumentNumber(productClientRequest.getDocumentNumber())
                                .flatMap(cliente->{
                                    log.info("Resultado de llamar al servicio de Clients: {}",cliente.toString());

                                    //Cliente empresarial puede tener multiples cuentas corrientes
                                    //Cliente personal solo puede tener 1 cuenta corriente
                                    if (valProdCli.stream().count() > 0 && cliente.getClientTypeDTO().getIdClientType().equals(Constantes.ClientTypePersonal))
                                        return Mono.error(new FunctionalException("Los clientes de tipo personal solo pueden tener una cuenta corriente"));

                                    return wcProductsService.findById(productClientRequest.getIdProduct())
                                            .flatMap(producto->{
                                                log.info("Resultado de llamar al servicio de Products: {}",producto.toString());

                                                if(!producto.getProductTypeDTO().getIdProductType().equals(Constantes.ProductTypePasivo))
                                                    return Mono.error(new FunctionalException("El producto no es Tipo Pasivo"));
                                                if(!producto.getProductSubTypeDTO().getIdProductSubType().equals(Constantes.ProductSubTypePasivoCuentaCorriente))
                                                    return Mono.error(new FunctionalException("El producto no es SubTipo Cuenta Corriente"));

                                                return productClientRepository.findByAccountNumber(productClientRequest.getAccountNumber()).flux().collectList()
                                                        .flatMap(y->{
                                                            if(y.stream().count() > 0)
                                                                return Mono.error(new FunctionalException("Existe un AccountNumber"));

                                                            ProductClient prdCli = productClientConvert.DTOToEntity2(productClientRequest,
                                                                    producto,cliente);

                                                            //Cuenta Corriente Empresarial Mype, costo de mantenimiento 0.00
                                                            if(cliente.getClientTypeDTO().getIdClientType().equals(Constantes.ClientTypeEmpresarialMype))
                                                                prdCli.setMaintenanceCost(0.00);

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
                });
    }
}
