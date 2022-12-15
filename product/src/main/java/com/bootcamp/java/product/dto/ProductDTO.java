package com.bootcamp.java.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private String id;
    private Integer idProduct;
    private String description;
    private Integer idProductType;
    private Integer idProductSubType;
    private Double transactionFee;
}
