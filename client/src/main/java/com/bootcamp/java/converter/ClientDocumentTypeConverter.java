package com.bootcamp.java.converter;

import com.bootcamp.java.dto.ClientDTO;
import com.bootcamp.java.dto.ClientDocumentTypeDTO;
import com.bootcamp.java.entity.ClientDocumentTypeEntity;
import com.bootcamp.java.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientDocumentTypeConverter {
    public ClientDocumentTypeEntity ConvertDTOToEntity(ClientDocumentTypeDTO clientDocumentTypeDTO){
        return ClientDocumentTypeEntity.builder()
                .id(clientDocumentTypeDTO.getId())
                .idClientDocumentType(clientDocumentTypeDTO.getIdClientDocumentType())
                .description(clientDocumentTypeDTO.getDescription())
                .build();
    }

    public ClientDocumentTypeDTO ConvertEntityToDTO(ClientDocumentTypeEntity clientDocumentTypeEntity) {
        return ClientDocumentTypeDTO.builder()
                .id(clientDocumentTypeEntity.getId())
                .idClientDocumentType(clientDocumentTypeEntity.getIdClientDocumentType())
                .description(clientDocumentTypeEntity.getDescription())
                .build();
    }
}
