package com.bootcamp.java.activoempresarial.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_ProductClient")
public class ProductClientEntity {
    @Id
    private String id;

    @NotNull
    private Integer idProduct;

    @NotNull
    private String productDescription;

    @NotNull
    private Integer idProductType;

    @NotNull
    private String productTypeDescription;

    @NotNull
    private Integer idProductSubType;

    @NotNull
    private String productSubTypeDescription;

    @NotNull
    private Integer idClient;

    @NotNull
    private Integer idClientType;

    @NotNull
    private String clientTypeDescription;

    @NotNull
    private Integer idClientDocumentType;

    @NotNull
    private String clientDocumentTypeDescription;

    @NotNull
    private String documentNumber;

    @NotNull
    private String fullName;

    @NotNull
    private String authorizedSigners;

    @NotNull
    private Integer creditLimit;

    @NotNull
    private Double balance;

    @NotNull
    private Double debt;

    @NotNull
    private Double maintenanceCost;

    @NotNull
    private Integer movementLimit;

    @NotNull
    private Integer credits;

}