package com.bootcamp.java.product.service.product;

import com.bootcamp.java.product.common.exceptionHandler.FunctionalException;
import com.bootcamp.java.product.converter.ProductConverter;
import com.bootcamp.java.product.dto.ProductDTO;
import com.bootcamp.java.product.dto.ProductResponseDTO;
import com.bootcamp.java.product.repository.ProductRepository;
import com.bootcamp.java.product.repository.ProductSubTypeRepository;
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
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductConverter productConverter;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private ProductSubTypeRepository productSubTypeRepository;

    @Override
    public Flux<ProductResponseDTO> findAll() {
        log.debug("findAll executing");
        return productRepository.findAll()
                .flatMap(prod-> productTypeRepository.findByIdProductType(prod.getIdProductType())
                        .flatMap(typeprod -> productSubTypeRepository.findByIdProductSubType(prod.getIdProductSubType())
                                .map(subtypeprod->productConverter.EntityToDTOResponse(prod, typeprod, subtypeprod))));
    }


    @Override
    public Mono<ProductResponseDTO> findById(Integer idProduct) {
        log.debug("findById executing {}", idProduct);
        return productRepository.findByIdProduct(idProduct)
                .flatMap(prod-> productTypeRepository.findByIdProductType(prod.getIdProductType())
                        .flatMap(typeprod -> productSubTypeRepository.findByIdProductSubType(prod.getIdProductSubType())
                                .map(subtypeprod->productConverter.EntityToDTOResponse(prod, typeprod, subtypeprod))));
    }

    @Override
    public Mono<ProductResponseDTO> create(ProductDTO productDTO) {
        log.debug("create executing {}", productDTO);

        return productTypeRepository.findByIdProductType(productDTO.getIdProductType())
                .switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El IdProductType %s no existe",productDTO.getIdProductType()))))
                .flatMap(tProd ->
                        productSubTypeRepository.findByIdProductSubType(productDTO.getIdProductSubType())
                                .switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El IdProductSubType %s no existe",productDTO.getIdProductSubType()))))
                                .flatMap(
                                        tsProd -> productRepository.save(productConverter.DTOtoEntity(productDTO))
                                                .map(prod -> productConverter.EntityToDTOResponse(prod, tProd, tsProd))
                                )
                );
    }

    @Override
    public Mono<ProductDTO> delete(Integer idProduct) {
        log.debug("delete executing {}", idProduct);
        return productRepository.findByIdProduct(idProduct)
                .flatMap(existingProduct -> {
                    log.debug("delete object executing {}", existingProduct);
                    return productRepository.delete(existingProduct)
                            .then(Mono.just(productConverter.EntityToDTO(existingProduct)));
                });
    }

    @Override
    public Mono<ProductResponseDTO> update(ProductDTO productDTO) {
        log.debug("update executing {}", productDTO);
        return productRepository.findByIdProduct(productDTO.getIdProduct())
                .flatMap(prd ->
                        productTypeRepository.findByIdProductType(productDTO.getIdProductType())
                                .flatMap(typeprod ->
                                        productSubTypeRepository.findByIdProductSubType(productDTO.getIdProductSubType())
                                                .flatMap(subtype ->
                                                        productRepository.save(productConverter.DTOtoEntity(productDTO,prd.getId()))
                                                        .map(a-> productConverter.EntityToDTOResponse(prd, typeprod, subtype))
                                                )
                                                .switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El IdProductSubType %s no existe",productDTO.getIdProductSubType())))))
                                .switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El IdProductType %s no existe",productDTO.getIdProductType())))))
                .switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El IdProduct %s no existe",productDTO.getIdProduct()))));
    }

}
