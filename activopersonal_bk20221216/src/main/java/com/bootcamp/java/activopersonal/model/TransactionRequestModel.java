package com.bootcamp.java.activopersonal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class TransactionRequestModel {
    @JsonIgnore
    private String id;

    private String idProductClient;

    @Min(1)
    @Max(6)
    private Integer idTransactionType;

    private Double mont;

    @JsonIgnore
    private Date registrationDate;

    @JsonIgnore
    private String sourceAccountNumber;

    @JsonIgnore
    private Integer OwnAccountNumber;

    private String destinationAccountNumber;

    private Integer destinationIdProduct;

    @JsonIgnore
    private Double transactionFee;
}
