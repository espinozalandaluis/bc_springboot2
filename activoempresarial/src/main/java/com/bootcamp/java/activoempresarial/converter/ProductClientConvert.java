package com.bootcamp.java.activoempresarial.converter;

import com.bootcamp.java.activoempresarial.dto.ProductClientDTO;
import com.bootcamp.java.activoempresarial.dto.webclients.client.ClientResponseDTO;
import com.bootcamp.java.activoempresarial.dto.webclients.product.ProductResponseDTO;
import com.bootcamp.java.activoempresarial.entity.ProductClientEntity;
import com.bootcamp.java.activoempresarial.model.MembershipRequestModel;
import org.springframework.stereotype.Component;

@Component
public class ProductClientConvert {

    public static ProductClientDTO EntityToDTO(ProductClientEntity productClient) {
        return ProductClientDTO.builder()
                .id(productClient.getId())
                .idProduct(productClient.getIdProduct())
                .productDescription(productClient.getProductDescription())
                .idProductType(productClient.getIdProductType())
                .productTypeDescription(productClient.getProductTypeDescription())
                .idProductSubType(productClient.getIdProductSubType())
                .productSubTypeDescription(productClient.getProductSubTypeDescription())
                .idClient(productClient.getIdClient())
                .idClientType(productClient.getIdClientType())
                .clientTypeDescription(productClient.getClientTypeDescription())
                .idClientDocumentType(productClient.getIdClientDocumentType())
                .clientDocumentTypeDescription(productClient.getClientDocumentTypeDescription())
                .documentNumber(productClient.getDocumentNumber())
                .fullName(productClient.getFullName())
                .authorizedSigners(productClient.getAuthorizedSigners())
                .creditLimit(productClient.getCreditLimit())
                .balance(productClient.getBalance())
                .debt(productClient.getDebt())
                .maintenanceCost(productClient.getMaintenanceCost())
                .movementLimit(productClient.getMovementLimit())
                .credits(productClient.getCredits())
                .build();
    }

    public static ProductClientEntity DTOToEntity(ProductClientDTO productClientDTO) {
        return ProductClientEntity.builder()
                .id(productClientDTO.getId())
                .idProduct(productClientDTO.getIdProduct())
                .productDescription(productClientDTO.getProductDescription())
                .idProductType(productClientDTO.getIdProductType())
                .productTypeDescription(productClientDTO.getProductTypeDescription())
                .idProductSubType(productClientDTO.getIdProductSubType())
                .productSubTypeDescription(productClientDTO.getProductSubTypeDescription())
                .idClient(productClientDTO.getIdClient())
                .idClientType(productClientDTO.getIdClientType())
                .clientTypeDescription(productClientDTO.getClientTypeDescription())
                .idClientDocumentType(productClientDTO.getIdClientDocumentType())
                .clientDocumentTypeDescription(productClientDTO.getClientDocumentTypeDescription())
                .documentNumber(productClientDTO.getDocumentNumber())
                .fullName(productClientDTO.getFullName())
                .authorizedSigners(productClientDTO.getAuthorizedSigners())
                .creditLimit(productClientDTO.getCreditLimit())
                .balance(productClientDTO.getBalance())
                .debt(productClientDTO.getDebt())
                .maintenanceCost(productClientDTO.getMaintenanceCost())
                .movementLimit(productClientDTO.getMovementLimit())
                .credits(productClientDTO.getCredits())
                .build();
    }

    public static ProductClientEntity PopulateProductClientEntityActive(ClientResponseDTO client,
                                                                 ProductResponseDTO product,
                                                                 MembershipRequestModel membership){
        return ProductClientEntity.builder()
                .idClient(client.getIdClient())
                .idProduct(membership.getIdProduct())
                .productDescription(product.getDescription())
                .idProductType(product.getProductTypeDTO().getIdProductType())
                .productTypeDescription(product.getProductTypeDTO().getDescription())
                .idProductSubType(product.getProductSubTypeDTO().getIdProductSubType())
                .productSubTypeDescription(product.getProductSubTypeDTO().getDescription())
                .idClientType(client.getClientTypeDTO().getIdClientType())
                .clientTypeDescription(client.getClientTypeDTO().getDescription())
                .idClientDocumentType(client.getClientDocumentTypeDTO().getIdClientDocumentType())
                .clientDocumentTypeDescription(client.getClientDocumentTypeDTO().getDescription())
                .documentNumber(membership.getDocumentNumber())
                .fullName(client.getFullName())
                .authorizedSigners(membership.getAuthorizedSigners())
                .creditLimit(membership.getCreditLimit())
                .balance(membership.getBalance())
                .debt(0.0)
                .maintenanceCost(0.0)
                .movementLimit(0)
                .credits(0)
                .build();
    }
}
