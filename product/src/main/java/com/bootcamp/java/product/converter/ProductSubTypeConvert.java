package com.bootcamp.java.product.converter;

import com.bootcamp.java.product.dto.ProductSubTypeDTO;
import com.bootcamp.java.product.entity.ProductSubType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductSubTypeConvert {

    public ProductSubTypeDTO EntityToDTO(ProductSubType productSubType) {
        return ProductSubTypeDTO.builder()
                .id(productSubType.getId())
                .idProductSubType(productSubType.getIdProductSubType())
                .idProductType(productSubType.getIdProductType())
                .description(productSubType.getDescription())
                .maintenanceCost(productSubType.getMaintenanceCost())
                .movementLimit(productSubType.getMovementLimit())
                .credits(productSubType.getCredits())
                .build();
    }

}
