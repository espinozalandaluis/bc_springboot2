package com.bootcamp.java.activoempresarial.dto;

import com.bootcamp.java.activoempresarial.entity.Transaction;
import lombok.Builder;
import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ProductClientTransactionDTO2 {
    private ProductClientDTO productClientDTO;
    private TransactionDTO transactionDTO;
}
