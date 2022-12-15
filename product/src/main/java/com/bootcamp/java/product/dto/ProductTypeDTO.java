package com.bootcamp.java.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductTypeDTO {

    private String id;
    private Integer idProductType;
    private String description;

}
