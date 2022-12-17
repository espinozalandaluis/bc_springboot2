package com.bootcamp.java.activopersonal.dto.webclients.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientTypeDTO {

    private String id;

    private int idClientType;

    private String description;
}