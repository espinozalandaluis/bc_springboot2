package com.bootcamp.java.activopersonal.model;

import lombok.Data;

@Data
public class MembershipRequestModel {
    private Integer idProduct;
    private String documentNumber;
    private Double creditLimit;
    private String accountNumber;
    //private String creditCardNumber;//TODO->Considerar para microservicio de tarjetas de credito
}
