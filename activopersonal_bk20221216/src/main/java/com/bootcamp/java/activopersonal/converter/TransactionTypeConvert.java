package com.bootcamp.java.activopersonal.converter;

import com.bootcamp.java.activopersonal.dto.TransactionTypeDTO;
import com.bootcamp.java.activopersonal.entity.TransactionTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeConvert {

    public static TransactionTypeDTO EntityToDTO(TransactionTypeEntity transactionType) {
        return TransactionTypeDTO.builder()
                .id(transactionType.getId())
                .idTransactionType(transactionType.getIdTransactionType())
                .description(transactionType.getDescription())
                .build();
    }

}
