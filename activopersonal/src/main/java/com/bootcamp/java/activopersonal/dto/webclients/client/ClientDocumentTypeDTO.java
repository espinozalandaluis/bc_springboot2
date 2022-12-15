package com.bootcamp.java.activopersonal.dto.webclients.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDocumentTypeDTO {

    private String id;

    private int idClientDocumentType;

    private String description;
}
