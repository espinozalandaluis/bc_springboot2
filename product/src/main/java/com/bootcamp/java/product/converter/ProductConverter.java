package com.bootcamp.java.product.converter;

import com.bootcamp.java.product.dto.ProductDTO;
import com.bootcamp.java.product.dto.ProductResponseDTO;
import com.bootcamp.java.product.dto.ProductSubTypeDTO;
import com.bootcamp.java.product.dto.ProductTypeDTO;
import com.bootcamp.java.product.entity.Product;
import com.bootcamp.java.product.entity.ProductSubType;
import com.bootcamp.java.product.entity.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductConverter {

    public Product DTOtoEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .description(productDTO.getDescription())
                .idProduct(productDTO.getIdProduct())
                .idProductType(productDTO.getIdProductType())
                .idProductSubType(productDTO.getIdProductSubType())
                .transactionFee(productDTO.getTransactionFee())
                .build();
    }

    public Product DTOtoEntity(ProductDTO productDTO, String id) {
        return Product.builder()
                .id(id)
                .description(productDTO.getDescription())
                .idProduct(productDTO.getIdProduct())
                .idProductType(productDTO.getIdProductType())
                .idProductSubType(productDTO.getIdProductSubType())
                .transactionFee(productDTO.getTransactionFee())
                .build();
    }

    public ProductDTO EntityToDTO(Product product) {
        return ProductDTO.builder()
                .idProduct(product.getIdProduct())
                .id(product.getId())
                .description(product.getDescription())
                .idProductType(product.getIdProductType())
                .idProductSubType(product.getIdProductSubType())
                .transactionFee(product.getTransactionFee())
                .build();
    }

    public ProductResponseDTO EntityToDTOResponse(Product product,
                                                  ProductType productType,
                                                  ProductSubType productSubType) {
        return ProductResponseDTO.builder()
                .idProduct(product.getIdProduct())
                .id(product.getId())
                .description(product.getDescription())
                .transactionFee(product.getTransactionFee())
                .productTypeDTO(ProductTypeDTO.builder()
                        .id(productType.getId())
                        .idProductType(productType.getIdProductType())
                        .description(productType.getDescription())
                        .build())
                .productSubTypeDTO(ProductSubTypeDTO.builder()
                        .id(productSubType.getId())
                        .idProductSubType(productSubType.getIdProductSubType())
                        .idProductType(productSubType.getIdProductType())
                        .description(productSubType.getDescription())
                        .maintenanceCost(productSubType.getMaintenanceCost())
                        .movementLimit(productSubType.getMovementLimit())
                        .credits(productSubType.getCredits())
                        .build())
                .build();
    }

}
