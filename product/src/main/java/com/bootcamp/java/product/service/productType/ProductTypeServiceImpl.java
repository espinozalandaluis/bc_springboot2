package com.bootcamp.java.product.service.productType;

import com.bootcamp.java.product.converter.ProductTypeConvert;
import com.bootcamp.java.product.dto.ProductTypeDTO;
import com.bootcamp.java.product.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService{

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    ProductTypeConvert productTypeConvert;

    @Override
    public Flux<ProductTypeDTO> findAll() {
        log.debug("findAll executing");
        Flux<ProductTypeDTO> dataProductTypeDTO = productTypeRepository.findAll()
                .map(ProductTypeConvert::EntityToDTO);
        return dataProductTypeDTO;
    }

    @Override
    public Mono<ProductTypeDTO> findById(Integer idProductType) {
        log.debug("findById executing {}", idProductType);
        Mono<ProductTypeDTO> dataMProductDTO = productTypeRepository.findByIdProductType(idProductType)
                .map(productType -> productTypeConvert.EntityToDTO(productType));
        log.debug("findById executed {}", dataMProductDTO);
        return dataMProductDTO;
    }

}
