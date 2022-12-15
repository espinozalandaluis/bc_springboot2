package com.bootcamp.java.activoempresarial.converter;

import com.bootcamp.java.activoempresarial.dto.TransactionDTO;
import com.bootcamp.java.activoempresarial.entity.ProductClientEntity;
import com.bootcamp.java.activoempresarial.entity.TransactionEntity;
import com.bootcamp.java.activoempresarial.model.MembershipRequestModel;
import com.bootcamp.java.activoempresarial.model.TransactionRequestModel;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionConvert {

    public static TransactionDTO EntityToDTO(TransactionEntity transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .idProductClient(transaction.getIdProductClient())
                .idTransactionType(transaction.getIdTransactionType())
                .mont(transaction.getMont())
                .registrationDate(transaction.getRegistrationDate())
                .build();
    }

    public static TransactionEntity DTOToEntity(TransactionDTO transactionDTO) {
        return TransactionEntity.builder()
                .id(transactionDTO.getId())
                .idProductClient(transactionDTO.getIdProductClient())
                .mont(transactionDTO.getMont())
                .registrationDate(transactionDTO.getRegistrationDate())
                .build();
    }

    public static TransactionEntity ModelToEntity(TransactionRequestModel transactionRequestModel) {
        Date currentDate = new Date();
        return TransactionEntity.builder()
                .idProductClient(transactionRequestModel.getIdProductClient())
                .idTransactionType(transactionRequestModel.getIdTransactionType())
                .mont(transactionRequestModel.getMont())
                .registrationDate(currentDate)
                .build();
    }

    public static TransactionEntity PopulateProductClientEntityActive(ProductClientEntity productClientEntity,
                                                                      MembershipRequestModel membership,
                                                                      Integer idTransactionType){
        Date currentDate = new Date();
        return TransactionEntity.builder()
                .idProductClient(productClientEntity.getId())
                .idTransactionType(idTransactionType)
                .mont(membership.getBalance())
                .registrationDate(currentDate)
                .build();
    }
}
