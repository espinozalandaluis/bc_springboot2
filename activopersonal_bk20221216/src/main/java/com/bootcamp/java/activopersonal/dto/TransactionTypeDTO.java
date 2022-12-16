package com.bootcamp.java.activopersonal.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionTypeDTO {

    private String id;
    private Integer idTransactionType;
    private String description;

}
