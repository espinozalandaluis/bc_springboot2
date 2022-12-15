package com.bootcamp.java.activoempresarial.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class TransactionRequestModel {
    private String idProductClient;
    @Min(1)
    @Max(4)
    private Integer idTransactionType;
    private Double mont;
}
