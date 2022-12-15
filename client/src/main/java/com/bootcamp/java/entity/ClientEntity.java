package com.bootcamp.java.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = {"idClient"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_client")
public class ClientEntity {
    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private Integer idClient;

    @NotNull
    private String documentNumber;

    @NotNull
    private String fullName;

    @NotNull
    private Integer idClientType;

    @NotNull
    private Integer idClientDocumentType;
}
