package com.bootcamp.java.pasivoahorro.service.transaction;

import com.bootcamp.java.pasivoahorro.common.Constantes;
import com.bootcamp.java.pasivoahorro.common.Funciones;
import com.bootcamp.java.pasivoahorro.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.pasivoahorro.converter.ProductClientConvert;
import com.bootcamp.java.pasivoahorro.converter.TransactionConvert;
import com.bootcamp.java.pasivoahorro.dto.*;
import com.bootcamp.java.pasivoahorro.entity.ProductClient;
import com.bootcamp.java.pasivoahorro.entity.Transaction;
import com.bootcamp.java.pasivoahorro.repository.ProductClientRepository;
import com.bootcamp.java.pasivoahorro.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductClientRepository productClientRepository;

    @Autowired
    TransactionConvert transactionConverter;

    @Autowired
    ProductClientConvert productClientConvert;

    @Override
    public Mono<TransactionDTO> registerTrx(TransactionDTO transactionDTO) {

        return productClientRepository.findById(transactionDTO.getIdProductClient())
                .flatMap(prodclient -> {
                    return transactionRepository.findTrxPerMoth(Funciones.GetFirstDayOfCurrentMonth()
                            ,transactionDTO.getIdProductClient()).collectList()
                            .flatMap(trxPerMonth -> {
                                log.info("Cantidad Trx Por Mes {}", trxPerMonth.stream().count());
                                log.info("getMovementLimit {}", prodclient.getMovementLimit());
                                if (trxPerMonth.stream().count() >= prodclient.getMovementLimit()) {
                                    return Mono.error(new FunctionalException("No se puede registrar trx por limite de transacciones"));
                                }
                                Transaction trx = transactionConverter.DTOtoEntity(transactionDTO);
                                return transactionRepository.save(trx)
                                        .flatMap(t->{
                                            prodclient.setBalance(CalculateBalance(prodclient.getBalance(),
                                                    trx.getMont(),
                                                    trx.getIdTransactionType(),
                                                    trx.getTransactionFee()));
                                            log.info("getBalance {}", prodclient.getBalance());

                                            return productClientRepository.save(prodclient)
                                                    .flatMap(x-> {
                                                        log.info("Actualizado el balance");
                                                        return transactionRepository.findById(t.getId())
                                                                .map(TransactionConvert::EntityToDTO);
                                                    });
                                        });
                            });
                })
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontro el producto")));
    }

    @Override
    public Mono<TransactionDTO> registerTrx2(TransactionRequestDTO transactionRequestDTO) {
        return productClientRepository.findById(transactionRequestDTO.getIdProductClient())
                .flatMap(prodclient -> {
                    return transactionRepository.findTrxPerMoth(Funciones.GetFirstDayOfCurrentMonth()
                                    ,transactionRequestDTO.getIdProductClient()).collectList()
                            .flatMap(trxPerMonth -> {

                                if (trxPerMonth.stream().count() >= prodclient.getMovementLimit()) {
                                    log.info("Cobro de comision por pasar limite de movimientos");
                                    transactionRequestDTO.setTransactionFee(prodclient.getTransactionFee());
                                }
                                else{
                                    log.info("NO Cobro de comision porque aun no pasa limite de movimientos");
                                    transactionRequestDTO.setTransactionFee(0.0);
                                }
                                transactionRequestDTO.setSourceAccountNumber(prodclient.getAccountNumber());

                                switch (transactionRequestDTO.getDestinationIdProduct()){
                                    case 1: {
                                        if(transactionRequestDTO.getIdTransactionType() == Constantes.TipoTrxDeposito ||
                                                transactionRequestDTO.getIdTransactionType() == Constantes.TipoTrxRetiro ||
                                                transactionRequestDTO.getIdTransactionType() == Constantes.TipoTrxConsumo)
                                        {
                                            log.info("Trx Pasivo Ahorro Deposito, Retiro o Consumo");
                                            transactionRequestDTO.setOwnAccountNumber(1); //A mi misma cuenta
                                            Transaction trx = transactionConverter.DTOtoEntity(transactionRequestDTO);
                                            return transactionRepository.save(trx)
                                                    .flatMap(t->{
                                                        prodclient.setBalance(CalculateBalance(prodclient.getBalance(),
                                                                trx.getMont(),
                                                                trx.getIdTransactionType(),
                                                                trx.getTransactionFee()));

                                                        return productClientRepository.save(prodclient)
                                                                .flatMap(x-> {
                                                                    log.info("Actualizado el balance");
                                                                    return transactionRepository.findById(t.getId())
                                                                            .map(TransactionConvert::EntityToDTO);
                                                                });
                                                    });
                                        }else if(transactionRequestDTO.getIdTransactionType() == Constantes.TipoTrxTransferenciaSalida){
                                            //REVISAR DESDE AQUI

                                            if(prodclient.getBalance() < (transactionRequestDTO.getMont() + transactionRequestDTO.getTransactionFee()))
                                                return Mono.error(() -> new FunctionalException("No tiene fondos suficientes para realizar la operacion"));
                                            return productClientRepository.findByAccountNumber(transactionRequestDTO.getDestinationAccountNumber())
                                                    .flatMap(xy -> {
                                                        if(xy.getAccountNumber().equals(prodclient.getAccountNumber()))
                                                            return Mono.error(() -> new FunctionalException("No puede realizar una transferencia a su misma cuenta de origen"));
                                                        if(xy.getDocumentNumber().equals(prodclient.getDocumentNumber())) {
                                                            transactionRequestDTO.setOwnAccountNumber(1); //La cuenta de destino le pertenece al mismo cliente
                                                        }else{
                                                            transactionRequestDTO.setOwnAccountNumber(0); //La cuenta de destino NO le pertenece al mismo cliente
                                                        }

                                                        Transaction trx = transactionConverter.DTOtoEntity(transactionRequestDTO);
                                                        return transactionRepository.save(trx)
                                                                .flatMap(t->{
                                                                    prodclient.setBalance(CalculateBalance(prodclient.getBalance(),
                                                                            trx.getMont(),
                                                                            trx.getIdTransactionType(),
                                                                            trx.getTransactionFee()));

                                                                    return productClientRepository.save(prodclient)
                                                                            .flatMap(x-> {
                                                                                log.info("Actualizado el balance");
                                                                                //AQUI AGREGAR LLAMADO AL API DE TRX
                                                                                return registerTrxEntrada(xy,trx)
                                                                                        .flatMap(xyz -> {
                                                                                            return Mono.just(transactionConverter.EntityToDTO(t));
                                                                                        });
                                                                                //return transactionRepository.findById(t.getId()).map(TransactionConvert::EntityToDTO);
                                                                            });
                                                                });

                                                        //return Mono.just(TransactionDTO.builder().build());
                                                    })
                                                    .switchIfEmpty(Mono.error(() -> new FunctionalException("La cuenta de destino es existe")));
                                        }else {
                                            log.info("Trx Pasivo Ahorro Otro");
                                            return Mono.error(() -> new FunctionalException("IdTransactionType no identificado"));
                                            //return Mono.just(TransactionDTO.builder().build());
                                        }
                                    }
                                    default: {
                                        return Mono.just(TransactionDTO.builder().build());
                                    }
                                }

                            });
                })
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontro el producto")));
    }


    public Mono<TransactionDTO> registerTrxEntrada(ProductClient productClient, Transaction transactionOrigen){

        transactionOrigen.setId(null);
        transactionOrigen.setIdTransactionType(Constantes.TipoTrxTransferenciaEntrada);
        transactionOrigen.setTransactionFee(0.00);

        return transactionRepository.save(transactionOrigen)
                .flatMap(x -> {
                    productClient.setBalance(CalculateBalance(productClient.getBalance(),
                            transactionOrigen.getMont(),
                            transactionOrigen.getIdTransactionType(),
                            transactionOrigen.getTransactionFee()));
                    return productClientRepository.save(productClient)
                            .flatMap(pc -> {
                                return Mono.just(transactionConverter.EntityToDTO(x));
                            });
                });
    }

    @Override
    public Flux<ProductClientTransactionDTO2> findByDocumentNumber(String documentNumber) {
        return productClientRepository.findByDocumentNumber(documentNumber)
                .flatMap(productocliente -> {
                    log.info("ProductClientTransactionDTO {}", productocliente);

                    return transactionRepository.findByIdProductClient(productocliente.getId())
                            .flatMap(trx -> {
                                return Flux.just(ProductClientTransactionDTO2.builder()
                                        .productClientDTO(productClientConvert.EntityToDTO(productocliente))
                                        .transactionDTO(transactionConverter.EntityToDTO(trx))
                                        .build());
                            });
                })
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontraron registros de productos afiliados")));
    }

    @Override
    public Flux<TransactionDTO> findAll() {
        log.debug("findAll executing");
        Flux<TransactionDTO> dataTransactionDTO = transactionRepository.findAll()
                .map(TransactionConvert::EntityToDTO);
        return dataTransactionDTO;
    }

    private Double CalculateBalance(Double ActualBalance, Double amountTrx, Integer transactionType, Double trxFee) {
        Double newBalance = 0.00;
        if(transactionType.equals(Constantes.TipoTrxRetiro)) //retiro
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxDeposito)) //deposito
            newBalance = ActualBalance + amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxConsumo)) //deposito
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxTransferenciaSalida)) //Transferencia a cuenta externa
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constantes.TipoTrxTransferenciaEntrada)) //Transferencia a cuenta externa
            newBalance = ActualBalance + amountTrx - trxFee;

        BigDecimal bd = new BigDecimal(newBalance).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }




}
