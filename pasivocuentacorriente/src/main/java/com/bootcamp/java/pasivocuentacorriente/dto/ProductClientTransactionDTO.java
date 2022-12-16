package com.bootcamp.java.pasivocuentacorriente.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductClientTransactionDTO {
    private ProductClientDTO productClientDTO;
    private TransactionDTO transactionDTO;
}
