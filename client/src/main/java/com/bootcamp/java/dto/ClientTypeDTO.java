package com.bootcamp.java.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientTypeDTO {

    private String id;

    private int idClientType;

    private String description;
}
