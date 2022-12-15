package com.bootcamp.java.activoempresarial.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"id"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_transaction")
public class TransactionEntity {

    @Id
    private String id;

    @NotNull
    private String idProductClient;

    @NotNull
    private Integer idTransactionType;

    @NotNull
    private Double mont;

    @NotNull
    private Date registrationDate;

}
