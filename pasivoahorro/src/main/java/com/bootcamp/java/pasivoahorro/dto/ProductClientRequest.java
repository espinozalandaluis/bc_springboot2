package com.bootcamp.java.pasivoahorro.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductClientRequest {
    private Integer idProduct;
    private String documentNumber;
    //private Integer movementLimit;
    private Double depositAmount;
    private String accountNumber ;
}
