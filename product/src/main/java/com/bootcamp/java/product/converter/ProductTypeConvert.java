package com.bootcamp.java.product.converter;


import com.bootcamp.java.product.dto.ProductTypeDTO;
import com.bootcamp.java.product.entity.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductTypeConvert {

    public static ProductTypeDTO EntityToDTO(ProductType productType) {
        return ProductTypeDTO.builder()
                .id(productType.getId())
                .idProductType(productType.getIdProductType())
                .description(productType.getDescription())
                .build();
    }

}
