package com.bootcamp.java.activopersonal.service.transaction;

import com.bootcamp.java.activopersonal.common.Constants;
import com.bootcamp.java.activopersonal.common.Funciones;
import com.bootcamp.java.activopersonal.common.exception.FunctionalException;
import com.bootcamp.java.activopersonal.converter.ProductClientConvert;
import com.bootcamp.java.activopersonal.converter.TransactionConvert;
import com.bootcamp.java.activopersonal.dto.ProductClientDTO;
import com.bootcamp.java.activopersonal.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import com.bootcamp.java.activopersonal.entity.ProductClientEntity;
import com.bootcamp.java.activopersonal.entity.TransactionEntity;
import com.bootcamp.java.activopersonal.model.TransactionRequestModel;
import com.bootcamp.java.activopersonal.repository.ProductClientRepository;
import com.bootcamp.java.activopersonal.repository.TransactionRepository;
import com.sun.tools.jconsole.JConsoleContext;
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
    ProductClientConvert productClientConvert;
    @Autowired
    TransactionConvert transactionConverter;

    @Override
    public Flux<ProductClientTransactionDTO> findByDocumentNumber(String documentNumber) {
        return productClientRepository.findByDocumentNumber(documentNumber)
                .flatMap(productocliente -> {
                    log.info("ProductClientTransactionDTO {}", productocliente);

                    return transactionRepository.findByIdProductClient(productocliente.getId())
                            .flatMap(trx -> {
                                return Flux.just(ProductClientTransactionDTO.builder()
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

    @Override
    public Mono<TransactionDTO> register(TransactionRequestModel transactionRequestModel) {
        log.info("Procedimiento para crear una nueva transaccion");
        log.info("======================>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(transactionRequestModel.toString());
        return productClientRepository.findById(transactionRequestModel.getIdProductClient())
                .flatMap(prodclient ->{
                    return transactionRepository.findTrxPerMoth(Funciones.GetFirstDayOfCurrentMonth(),transactionRequestModel.getIdProductClient())
                            .collectList()
                            .flatMap(trxPerMonth ->{
                                if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Deposito ||
                                        transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Retiro ||
                                        transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Consumo ||
                                        transactionRequestModel.getIdTransactionType() == Constants.TransactionType.TransferenciaSalida)
                                    return Mono.error(() -> new FunctionalException("Error, tipo de transaccion no admitida"));
                                if(transactionRequestModel.getMont() <= 0.009)
                                    return Mono.error(() -> new FunctionalException("El monto debe ser mayor a 0.00"));
                                if (trxPerMonth.stream().count() >= prodclient.getMovementLimit()) {
                                    log.info("Cobro de comision por pasar limite de movimientos");
                                    transactionRequestModel.setTransactionFee(prodclient.getTransactionFee());
                                }
                                else{
                                    log.info("NO Cobro de comision porque aun no pasa limite de movimientos");
                                    transactionRequestModel.setTransactionFee(0.0);
                                }
                                transactionRequestModel.setSourceAccountNumber(prodclient.getAccountNumber());
                                if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Deposito ||
                                        transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Retiro ||
                                        transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Consumo){
                                    if((transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Retiro ||
                                            transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Consumo) &&
                                            (prodclient.getBalance() < (transactionRequestModel.getMont() + transactionRequestModel.getTransactionFee()))){
                                        log.info("No tiene fondos suficientes para realizar la operacion");
                                        return Mono.error(() -> new FunctionalException("No tiene fondos suficientes para realizar la operacion"));
                                    }
                                    log.info("Trx Pasivo Ahorro Deposito, Retiro o Consumo");
                                    transactionRequestModel.setOwnAccountNumber(1); //A mi misma cuenta
                                    transactionRequestModel.setDestinationAccountNumber(null);
                                    transactionRequestModel.setDestinationIdProduct(Constants.ProductSubType.CreditoPersonal);
                                    TransactionEntity transactionEntity = transactionConverter.ModelToEntity(transactionRequestModel);
                                    return transactionRepository.save(transactionEntity)
                                            .flatMap(transaction ->{
                                                prodclient.setBalance(CalculateBalance(prodclient.getBalance(),
                                                        transaction.getMont(),
                                                        transaction.getIdTransactionType(),
                                                        transaction.getTransactionFee()));
                                                return productClientRepository.save(prodclient)
                                                        .flatMap(x ->{
                                                            log.info("Actualizado el balance");
                                                            return transactionRepository.findById(transaction.getId())
                                                                    .map(TransactionConvert::EntityToDTO);
                                                        });
                                            })
                                            .switchIfEmpty(Mono.error(() -> new FunctionalException("Hubo un problema al guardar la transaccion")));
                                }

                                if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.TransferenciaSalida)
                                    if(prodclient.getBalance() < (transactionRequestModel.getMont() + transactionRequestModel.getTransactionFee()))
                                        return Mono.error(() -> new FunctionalException("No tiene fondos suficientes para realizar la operacion"));

                                switch (transactionRequestModel.getDestinationIdProduct()) {
                                    case 4:{
                                        if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.TransferenciaSalida){
                                            if(prodclient.getBalance() < (transactionRequestModel.getMont() + transactionRequestModel.getTransactionFee()))
                                                return Mono.error(() -> new FunctionalException("No tiene fondos suficientes para realizar la operacion"));
                                            return productClientRepository.findByAccountNumber(transactionRequestModel.getDestinationAccountNumber())
                                                    .flatMap(xy ->{
                                                        if(xy.getAccountNumber().equals(prodclient.getAccountNumber()))
                                                            return Mono.error(() -> new FunctionalException("No puede realizar una transferencia a su misma cuenta de origen"));
                                                        if(xy.getDocumentNumber().equals(prodclient.getDocumentNumber())) {
                                                            transactionRequestModel.setOwnAccountNumber(1); //La cuenta de destino le pertenece al mismo cliente
                                                        }else{
                                                            transactionRequestModel.setOwnAccountNumber(0); //La cuenta de destino NO le pertenece al mismo cliente
                                                        }
                                                        TransactionEntity trx = transactionConverter.ModelToEntity(transactionRequestModel);
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
                                                                            });
                                                                });
                                                    })
                                                    .switchIfEmpty(Mono.error(() -> new FunctionalException("La cuenta de destino es existe")));
                                        }
                                    }
                                    default: {
                                        return Mono.error(() -> new FunctionalException("El destinationIdProduct especificado no a sido implementado"));
                                    }
                                }
                            })
                            .switchIfEmpty(Mono.error(() -> new FunctionalException("Hubo un problema al obtener las transacciones por mes")));

                })
                .switchIfEmpty(Mono.error(() -> new FunctionalException("No se encontro el producto")));
    }

    @Override
    public Mono<TransactionDTO> registerTrxEntradaExterna(TransactionDTO transactionDTO, String IdProductClient) {

        return transactionRepository.save(transactionConverter.DTOToEntity(transactionDTO))
                .flatMap(trx -> {
                    return productClientRepository.findById(IdProductClient)
                            .flatMap(productClient -> {
                                productClient.setBalance(CalculateBalance(productClient.getBalance(),
                                        transactionDTO.getMont(),
                                        Constants.TransactionType.TransferenciaEntrada,0.0));

                                return productClientRepository.save(productClient)
                                        .flatMap(prdcli -> {
                                            return Mono.just(transactionConverter.EntityToDTO(trx));
                                        });
                            })
                            .switchIfEmpty(Mono.error(() -> new FunctionalException("Error, No se encontro producto")));
                })
                .switchIfEmpty(Mono.error(() -> new FunctionalException("Error al registrar la trx de entrada")));
    }
    public Mono<TransactionDTO> registerTrxEntrada(ProductClientEntity productClient, TransactionEntity transactionOrigen){

        transactionOrigen.setId(null);
        transactionOrigen.setIdTransactionType(Constants.TransactionType.TransferenciaEntrada);
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
    private Double CalculateBalance(Double ActualBalance, Double amountTrx, Integer transactionType, Double trxFee) {
        Double newBalance = 0.00;
        if(transactionType.equals(Constants.TransactionType.Retiro)) //retiro
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constants.TransactionType.Deposito)) //deposito
            newBalance = ActualBalance + amountTrx - trxFee;

        if(transactionType.equals(Constants.TransactionType.Consumo)) //deposito
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constants.TransactionType.TransferenciaSalida)) //Transferencia a cuenta externa
            newBalance = ActualBalance - amountTrx - trxFee;

        if(transactionType.equals(Constants.TransactionType.TransferenciaEntrada)) //Transferencia a cuenta externa
            newBalance = ActualBalance + amountTrx - trxFee;

        BigDecimal bd = new BigDecimal(newBalance).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /*
    @Override
    public Mono<ProductClientTransactionDTO> create(TransactionRequestModel transactionRequestModel) {
        log.info("Procedimiento para crear una nueva transaccion");
        log.info("======================>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(transactionRequestModel.toString());
        return productClientRepository.findById(transactionRequestModel.getIdProductClient())
                .flatMap(prdCli -> {
                    double amount = 0.0;

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Consumo && transactionRequestModel.getMont() > prdCli.getBalance())
                        return Mono.error(new FunctionalException("El monto ingresado es mayor al disponible"));

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Pago && transactionRequestModel.getMont() > prdCli.getBalance())
                        return Mono.error(new FunctionalException("No se puede pagar el credito porque el monto ingresado es mayor"));

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Consumo || transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Retiro)
                        amount = prdCli.getBalance() - transactionRequestModel.getMont();

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Deposito || transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Pago)
                        amount = prdCli.getBalance() + transactionRequestModel.getMont();

                    double finalAmount = amount;
                    log.info("Amount {}" , finalAmount);
                    return transactionRepository.save(transactionConverter.ModelToEntity(transactionRequestModel,0.0))
                            .flatMap(transaction -> {
                                prdCli.setBalance(finalAmount);
                                log.info("El DTO para actualizar es: {}",prdCli.toString());
                                return productClientRepository.save(prdCli)
                                        .flatMap(productClient ->{
                                            return Mono.just(ProductClientTransactionDTO.builder()
                                                    .transactionDTO(transactionConverter.EntityToDTO(transaction))
                                                    .productClientDTO(productClientConvert.EntityToDTO(prdCli))
                                                    .build());
                                        });
                            }).switchIfEmpty(Mono.error(() -> new FunctionalException("Ocurrio un error al guardar la transaccion")));

                }).switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El Id %s ingresado no ha retornado ningun valor",transactionRequestModel.getIdProductClient()))));
    }
     */
}
