package com.bootcamp.java.product.service.productSubType;

import com.bootcamp.java.product.converter.ProductSubTypeConvert;
import com.bootcamp.java.product.dto.ProductSubTypeDTO;
import com.bootcamp.java.product.repository.ProductSubTypeRepository;
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
public class ProductSubTypeServiceImpl implements  ProductSubTypeService{


    @Autowired
    private ProductSubTypeRepository productSubTypeRepository;

    @Autowired
    ProductSubTypeConvert productSubTypeConvert;

    @Override
    public Flux<ProductSubTypeDTO> findAll() {
        log.debug("findAll executing");
        Flux<ProductSubTypeDTO> dataProductSubTypeDTO = productSubTypeRepository.findAll()
                .map(productSubType -> productSubTypeConvert.EntityToDTO(productSubType));
        log.debug("findAll executed");
        return dataProductSubTypeDTO;
    }

    @Override
    public Mono<ProductSubTypeDTO> findById(Integer idProductSubType) {
        log.debug("findById executing {}", idProductSubType);
        Mono<ProductSubTypeDTO> dataProductSubTypeDTO = productSubTypeRepository.findByIdProductSubType(idProductSubType)
                .map(productSubType -> {
                    log.debug("findById executing object {}", productSubType);
                    return productSubTypeConvert.EntityToDTO(productSubType);});
        log.debug("findById executed {}", dataProductSubTypeDTO);
        return dataProductSubTypeDTO;
    }
}
