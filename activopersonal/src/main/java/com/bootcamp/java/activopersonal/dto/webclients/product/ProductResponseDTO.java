package com.bootcamp.java.activopersonal.dto.webclients.product;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponseDTO {
    private String id;
    private Integer idProduct;
    private String description;
    private ProductTypeDTO productTypeDTO;
    private ProductSubTypeDTO productSubTypeDTO;
    private Double transactionFee;
}
