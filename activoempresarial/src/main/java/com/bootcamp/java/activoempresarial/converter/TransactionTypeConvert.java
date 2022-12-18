package com.bootcamp.java.activoempresarial.converter;

import com.bootcamp.java.activoempresarial.dto.TransactionTypeDTO;
import com.bootcamp.java.activoempresarial.entity.TransactionType;
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
