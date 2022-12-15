package com.bootcamp.java.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDocumentTypeDTO {

    private String id;

    private int idClientDocumentType;

    private String description;
}
