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
@EqualsAndHashCode(of = {"IdClientDocumentType"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "tbl_clientDocumentType")
public class ClientDocumentTypeEntity {
    @Id
    @JsonIgnore
    private String id;

    @NotNull
    @Indexed(unique = true)
    private int idClientDocumentType;

    @NotNull
    private String description;
}
