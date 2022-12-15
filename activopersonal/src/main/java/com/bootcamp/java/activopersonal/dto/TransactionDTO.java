package com.bootcamp.java.activopersonal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TransactionDTO {
    private String id;
    private String idProductClient;
    private Integer idTransactionType;
    private Double mont;
    private String destinationAccountNumber;
    private String sourceAccountNumber;
    private Integer ownAccountTransfers;
    private Date registrationDate;
}