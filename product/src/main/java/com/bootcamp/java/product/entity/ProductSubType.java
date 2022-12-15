package com.bootcamp.java.product.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"idProductSubType"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_productSubType")
public class ProductSubType {

    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private Integer idProductSubType;

    //Prueba
    @NotNull
    private Integer idProductType;

    @NotNull
    private String description;

    @NotNull
    private Double maintenanceCost;

    @NotNull
    private Integer movementLimit;

    @NotNull
    private Integer credits;

}
