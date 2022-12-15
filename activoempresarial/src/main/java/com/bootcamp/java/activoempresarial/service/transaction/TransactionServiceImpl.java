package com.bootcamp.java.activoempresarial.service.transaction;

import com.bootcamp.java.activoempresarial.common.Constants;
import com.bootcamp.java.activoempresarial.common.exception.FunctionalException;
import com.bootcamp.java.activoempresarial.converter.ProductClientConvert;
import com.bootcamp.java.activoempresarial.converter.TransactionConvert;
import com.bootcamp.java.activoempresarial.dto.ProductClientDTO;
import com.bootcamp.java.activoempresarial.dto.ProductClientTransactionDTO;
import com.bootcamp.java.activoempresarial.dto.TransactionDTO;
import com.bootcamp.java.activoempresarial.model.TransactionRequestModel;
import com.bootcamp.java.activoempresarial.repository.ProductClientRepository;
import com.bootcamp.java.activoempresarial.repository.TransactionRepository;
import com.sun.tools.jconsole.JConsoleContext;
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
    public Flux<TransactionDTO> findAll() {
        log.debug("findAll executing");
        Flux<TransactionDTO> dataTransactionDTO = transactionRepository.findAll()
                .map(TransactionConvert::EntityToDTO);
        return dataTransactionDTO;
    }

    @Override
    public Mono<TransactionDTO> findById(String Id) {
        log.debug("findById executing {}", Id);
        Mono<TransactionDTO> dataTransactionDTO = transactionRepository.findById(Id)
                .map(trxType -> transactionConverter.EntityToDTO(trxType));
        log.debug("findById executed {}", dataTransactionDTO);
        return dataTransactionDTO;
    }

    @Override
    public Mono<ProductClientTransactionDTO> create(TransactionRequestModel transactionRequestModel) {
        log.info("Procedimiento para crear una nueva transaccion");
        log.info("======================>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(transactionRequestModel.toString());
        return productClientRepository.findById(transactionRequestModel.getIdProductClient())
                .flatMap(prdCli -> {
                    double amount = 0.0;

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Retiro)
                        return Mono.error(new FunctionalException("El TransactionType no disponible"));

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Consumo && transactionRequestModel.getMont() > prdCli.getBalance())
                        return Mono.error(new FunctionalException("El monto ingresado es mayor al disponible"));

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Pago && transactionRequestModel.getMont() > prdCli.getBalance())
                        return Mono.error(new FunctionalException("No se puede pagar el credito porque el monto ingresado es mayor"));

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Consumo)
                        amount = prdCli.getBalance() - transactionRequestModel.getMont();

                    if(transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Deposito || transactionRequestModel.getIdTransactionType() == Constants.TransactionType.Pago)
                        amount = prdCli.getBalance() + transactionRequestModel.getMont();

                    double finalAmount = amount;
                    log.info("Amount {}" , finalAmount);
                    return transactionRepository.save(transactionConverter.ModelToEntity(transactionRequestModel))
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
}
