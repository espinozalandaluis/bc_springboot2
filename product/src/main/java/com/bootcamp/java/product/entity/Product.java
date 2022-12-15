package com.bootcamp.java.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

//@Data
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(of = {"idProduct"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_product")
public class Product {

    @Id
    @JsonIgnore
    private String id;

    @NotNull
    @Indexed(unique = true)
    private Integer idProduct;

    @NotNull
    private Integer idProductType;

    @NotNull
    private Integer idProductSubType;

    @NotNull
    private String description;

    @NotNull
    private Double transactionFee;

}
