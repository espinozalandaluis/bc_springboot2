package com.bootcamp.java.activopersonal.service.transactionType;

import com.bootcamp.java.activopersonal.converter.TransactionTypeConvert;
import com.bootcamp.java.activopersonal.dto.TransactionTypeDTO;
import com.bootcamp.java.activopersonal.repository.TransactionTypeRepository;
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
public class TransactionTypeServiceImpl implements TransactionTypeService {

    @Autowired
    private TransactionTypeRepository transactionTypeRepository;

    @Autowired
    TransactionTypeConvert transactionTypeConvert;

    @Override
    public Flux<TransactionTypeDTO> findAll() {
        log.debug("findAll executing");
        Flux<TransactionTypeDTO> dataProductTypeDTO = transactionTypeRepository.findAll()
                .map(TransactionTypeConvert::EntityToDTO);
        return dataProductTypeDTO;
    }

    @Override
    public Mono<TransactionTypeDTO> findById(Integer IdTransactionType) {
        log.debug("findById executing {}", IdTransactionType);
        Mono<TransactionTypeDTO> dataTransactionTypeDTO = transactionTypeRepository.findByIdTransactionType(IdTransactionType)
                .map(trxType -> transactionTypeConvert.EntityToDTO(trxType));
        log.debug("findById executed {}", dataTransactionTypeDTO);
        return dataTransactionTypeDTO;
    }
}
