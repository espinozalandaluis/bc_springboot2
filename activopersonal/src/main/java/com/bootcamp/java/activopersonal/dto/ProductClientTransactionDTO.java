package com.bootcamp.java.activopersonal.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProductClientTransactionDTO {
    private ProductClientDTO productClientDTO;
    private TransactionDTO transactionDTO;
}
