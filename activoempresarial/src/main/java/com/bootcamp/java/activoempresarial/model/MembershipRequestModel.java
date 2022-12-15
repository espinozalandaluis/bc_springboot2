package com.bootcamp.java.activoempresarial.model;

import lombok.Data;

@Data
public class MembershipRequestModel {
    private Integer idProduct;
    private String documentNumber;
    private String authorizedSigners;
    private Integer creditLimit;
    private Double balance;
    private Integer credits;
}
