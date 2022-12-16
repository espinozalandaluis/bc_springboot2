package com.bootcamp.java.activopersonal.converter;

import com.bootcamp.java.activopersonal.common.Constants;
import com.bootcamp.java.activopersonal.dto.TransactionDTO;
import com.bootcamp.java.activopersonal.entity.ProductClientEntity;
import com.bootcamp.java.activopersonal.entity.TransactionEntity;
import com.bootcamp.java.activopersonal.model.MembershipRequestModel;
import com.bootcamp.java.activopersonal.model.TransactionRequestModel;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.Date;

import static com.bootcamp.java.activopersonal.common.Constants.TransactionFreeFee;

@Component
public class TransactionConvert {

    public static TransactionDTO EntityToDTO(TransactionEntity transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .idProductClient(transaction.getIdProductClient())
                .idTransactionType(transaction.getIdTransactionType())
                .mont(transaction.getMont())
                .destinationAccountNumber(transaction.getDestinationAccountNumber())
                .sourceAccountNumber(transaction.getDestinationAccountNumber())
                .ownAccountNumber(transaction.getOwnAccountNumber())
                .registrationDate(transaction.getRegistrationDate())
                .build();
    }

    public static TransactionEntity DTOToEntity(TransactionDTO transactionDTO) {
        return TransactionEntity.builder()
                .id(transactionDTO.getId())
                .idProductClient(transactionDTO.getIdProductClient())
                .mont(transactionDTO.getMont())
                .destinationAccountNumber(transactionDTO.getDestinationAccountNumber())
                .sourceAccountNumber(transactionDTO.getSourceAccountNumber())
                .ownAccountNumber(transactionDTO.getOwnAccountNumber())
                .registrationDate(transactionDTO.getRegistrationDate())
                .build();
    }

    public static TransactionEntity ModelToEntity(TransactionRequestModel transactionRequestModel) {
        Date currentDate = new Date();
        return TransactionEntity.builder()
                .idProductClient(transactionRequestModel.getIdProductClient())
                .idTransactionType(transactionRequestModel.getIdTransactionType())
                .mont(transactionRequestModel.getMont())
                .destinationAccountNumber(transactionRequestModel.getDestinationAccountNumber())
                .sourceAccountNumber(transactionRequestModel.getSourceAccountNumber())
                .ownAccountNumber(transactionRequestModel.getOwnAccountNumber())
                .transactionFee(transactionRequestModel.getTransactionFee())
                .registrationDate(currentDate)
                .build();
    }

    public static TransactionEntity PopulateProductClientEntityActive(ProductClientEntity productClientEntity,
                                                                      MembershipRequestModel membership){
        Date currentDate = new Date();
        return TransactionEntity.builder()
                .idProductClient(productClientEntity.getId())
                .idTransactionType(Constants.TransactionType.Deposito)
                .mont(membership.getCreditLimit())
                .sourceAccountNumber(productClientEntity.getAccountNumber())
                .destinationAccountNumber(productClientEntity.getAccountNumber())
                .ownAccountNumber(Constants.OwnAccountNumber.Si)
                .transactionFee(TransactionFreeFee)
                .registrationDate(currentDate)
                .build();
    }
}
