package com.bootcamp.java.dto;

import com.bootcamp.java.entity.ClientDocumentTypeEntity;
import com.bootcamp.java.entity.ClientTypeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ClientDTO {

    private String id;

    private Integer idClient;

    private String documentNumber;

    private String fullName;

    private Integer idClientType;

    private Integer idClientDocumentType;

}
