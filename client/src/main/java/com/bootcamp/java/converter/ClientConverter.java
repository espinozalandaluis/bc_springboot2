package com.bootcamp.java.converter;

import com.bootcamp.java.dto.ClientDocumentTypeDTO;
import com.bootcamp.java.dto.ClientResponseDTO;
import com.bootcamp.java.dto.ClientTypeDTO;
import com.bootcamp.java.entity.ClientDocumentTypeEntity;
import com.bootcamp.java.entity.ClientEntity;
import com.bootcamp.java.dto.ClientDTO;
import com.bootcamp.java.entity.ClientTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter {
    public ClientEntity ConvertDTOToEntity(ClientDTO clientDTO){
        return ClientEntity.builder()
                .id(clientDTO.getId())
                .idClient(clientDTO.getIdClient())
                .fullName(clientDTO.getFullName())
                .documentNumber(clientDTO.getDocumentNumber())
                .idClientType(clientDTO.getIdClientType())
                .idClientDocumentType(clientDTO.getIdClientDocumentType())
                .build();
    }

    public ClientDTO ConvertEntityToDTO(ClientEntity clientEntity) {
        return  ClientDTO.builder()
                .id(clientEntity.getId())
                .idClient(clientEntity.getIdClient())
                .fullName(clientEntity.getFullName())
                .documentNumber(clientEntity.getDocumentNumber())
                .idClientType(clientEntity.getIdClientType())
                .idClientDocumentType(clientEntity.getIdClientDocumentType())
                .build();
    }

    public ClientResponseDTO EntityToDTOResponse(ClientEntity clientEntity,
                                                 ClientTypeEntity clientTypeEntity,
                                                 ClientDocumentTypeEntity clientDocumentType){
        return ClientResponseDTO.builder()
                .id(clientEntity.getId())
                .idClient(clientEntity.getIdClient())
                .fullName(clientEntity.getFullName())
                .documentNumber(clientEntity.getDocumentNumber())
                .clientTypeDTO(ClientTypeDTO.builder()
                        .id(clientTypeEntity.getId())
                        .idClientType(clientTypeEntity.getIdClientType())
                        .description(clientTypeEntity.getDescription())
                        .build())
                .clientDocumentTypeDTO(ClientDocumentTypeDTO.builder()
                        .id(clientDocumentType.getId())
                        .idClientDocumentType(clientDocumentType.getIdClientDocumentType())
                        .description(clientDocumentType.getDescription())
                        .build())
                .build();
    }
}
