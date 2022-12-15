package com.bootcamp.java.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSubTypeDTO {

    private String id;

    private Integer idProductSubType;

    //Prueba
    private Integer idProductType;
    private String description;

    private Double maintenanceCost;

    private Integer movementLimit;

    private Integer credits;
}
