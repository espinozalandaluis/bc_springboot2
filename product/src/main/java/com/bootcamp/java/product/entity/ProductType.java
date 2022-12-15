package com.bootcamp.java.product.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"idProductType"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_productType")
public class ProductType {

    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private Integer idProductType;

    @NotNull
    private String description;

    /*
    @NotNull
    private ProductSubType productSubType;
     */
}
