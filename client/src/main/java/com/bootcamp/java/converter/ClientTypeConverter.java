package com.bootcamp.java.converter;

import com.bootcamp.java.dto.ClientDTO;
import com.bootcamp.java.dto.ClientTypeDTO;
import com.bootcamp.java.entity.ClientTypeEntity;
import com.bootcamp.java.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientTypeConverter {
    public ClientTypeEntity ConvertDTOToEntity(ClientTypeDTO clientTypeDTO){
        return ClientTypeEntity.builder()
                .id(clientTypeDTO.getId())
                .idClientType(clientTypeDTO.getIdClientType())
                .description(clientTypeDTO.getDescription())
                .build();
    }

    public ClientTypeDTO ConvertEntityToDTO(ClientTypeEntity clientTypeEntity) {
        return ClientTypeDTO.builder()
                .id(clientTypeEntity.getId())
                .idClientType(clientTypeEntity.getIdClientType())
                .description(clientTypeEntity.getDescription())
                .build();
    }
}
