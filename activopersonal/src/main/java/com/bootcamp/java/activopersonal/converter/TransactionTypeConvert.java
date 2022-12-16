package com.bootcamp.java.activopersonal.converter;

import com.bootcamp.java.activopersonal.dto.TransactionTypeDTO;
import com.bootcamp.java.activopersonal.entity.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class TransactionTypeConvert {
    public static TransactionTypeDTO EntityToDTO(TransactionType transactionType) {
        return TransactionTypeDTO.builder()
                .id(transactionType.getId())
                .idTransactionType(transactionType.getIdTransactionType())
                .description(transactionType.getDescription())
                .build();
    }
}
