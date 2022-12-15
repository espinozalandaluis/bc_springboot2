package com.bootcamp.java.activopersonal.service.productClient;

import com.bootcamp.java.activopersonal.common.Constants;
import com.bootcamp.java.activopersonal.common.exception.FunctionalException;
import com.bootcamp.java.activopersonal.converter.ProductClientConvert;
import com.bootcamp.java.activopersonal.converter.TransactionConvert;
import com.bootcamp.java.activopersonal.dto.ProductClientDTO;
import com.bootcamp.java.activopersonal.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import com.bootcamp.java.activopersonal.model.MembershipRequestModel;
import com.bootcamp.java.activopersonal.repository.ProductClientRepository;
import com.bootcamp.java.activopersonal.repository.TransactionRepository;
import com.bootcamp.java.activopersonal.service.webClients.client.WcClientsService;
import com.bootcamp.java.activopersonal.service.webClients.product.WcProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

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
    ProductClientConvert productClientConvert;

    @Autowired
    TransactionConvert transactionConvert;
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
    public Mono<ProductClientDTO> findById(String Id) {
        log.debug("findById executing {}", Id);
        Mono<ProductClientDTO> dataProductClientDTO = productClientRepository.findById(Id)
                .map(prdCli -> productClientConvert.EntityToDTO(prdCli));
        log.debug("findById executed {}", Id);
        return dataProductClientDTO;
    }

    @Override
    public Mono<ProductClientTransactionDTO> create(MembershipRequestModel membershipRequestModel) {
        log.info("Procedimiento para crear una nueva afiliacion");
        log.info("======================>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(membershipRequestModel.toString());
        return productClientRepository.findByDocumentNumber(membershipRequestModel.getDocumentNumber()).collectList()
                .flatMap(valProdCli ->{
                    if(valProdCli.stream().count()==0){
                        return wcClientsService.findById(membershipRequestModel.getDocumentNumber())
                                .flatMap(client->{
                                    log.info("Resultado de llamar al servicio de Clients: {}",client.toString());
                                    if(client.getClientTypeDTO().getIdClientType() != Constants.ClientType.Personal)
                                        return Mono.error(new FunctionalException("El tipo de cliente no es personal"));
                                    if(client.getClientDocumentTypeDTO().getIdClientDocumentType() != Constants.ClientDocumentType.DNI)
                                        return Mono.error(new FunctionalException("El tipo de documento del cliente no es DNI"));

                                    return wcProductsService.findById(membershipRequestModel.getIdProduct())
                                            .flatMap(product->{
                                                log.info("Resultado de llamar al servicio de Products: {}",product.toString());
                                                if(product.getProductTypeDTO().getIdProductType() != Constants.ProductType.Activos)
                                                    return Mono.error(new FunctionalException("El tipo de producto no pertenece al grupo de Activos"));
                                                if(product.getProductSubTypeDTO().getIdProductSubType() != Constants.ProductSubType.CreditoPersonal)
                                                    return Mono.error(new FunctionalException("El subtipo de producto no pertenece a credito personal"));

                                                return productClientRepository
                                                        .save(productClientConvert.PopulateProductClientEntityActive(client,product,membershipRequestModel))
                                                        .flatMap(productClient -> {
                                                            log.info("Resultado de guardar ProductClient: {}",productClient.toString());
                                                            if(membershipRequestModel.getCreditLimit() > 0){
                                                                return transactionRepository
                                                                        .save(transactionConvert.PopulateProductClientEntityActive(productClient,membershipRequestModel,Constants.TransactionType.Deposito))
                                                                        .flatMap(transaction ->{
                                                                            log.info("Resultado de guardar Transactions: {}",transaction.toString());
                                                                            return Mono.just(ProductClientTransactionDTO.builder()
                                                                                    .productClientDTO(productClientConvert.EntityToDTO(productClient))
                                                                                    .transactionDTO(transactionConvert.EntityToDTO(transaction))
                                                                                    .build());
                                                                        }).switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al guardar el Transaction")));
                                                            }
                                                            else{
                                                                return Mono.just(ProductClientTransactionDTO.builder()
                                                                        .productClientDTO(productClientConvert.EntityToDTO(productClient))
                                                                        .build());
                                                            }
                                                        }).switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al guardar el ProductClient")));
                                            }).switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al consultar el servicio de producto")));
                                }).switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al consultar el servicio de cliente")));
                    }
                    else{
                        return Mono.error(new FunctionalException(String.format("Ya existe una afiliacion asociada con el DocumentNumber: %s",membershipRequestModel.getDocumentNumber())));
                    }
                });
    }
}
















/*
                                return productClientRepository.findByDocumentNumber(afiliacionRequestModel.getDocumentNumber()).collectList()
                                        .flatMap(z->{
                                            if(z.stream().count()>1)
                                                return Mono.error(new FunctionalException("El subtipo de producto no pertenece a credito personal"));
                                        });
 */