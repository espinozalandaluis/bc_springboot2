package com.bootcamp.java.activoempresarial.dto.webclients.product;

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
}
