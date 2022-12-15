package com.bootcamp.java.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDTO {
    private String id;
    private Integer idProduct;
    private String description;
    private Double transactionFee;
    private ProductTypeDTO productTypeDTO;
    private ProductSubTypeDTO productSubTypeDTO;
}
