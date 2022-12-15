package com.bootcamp.java.service.clientDocumentType;

import com.bootcamp.java.converter.ClientDocumentTypeConverter;
import com.bootcamp.java.dto.ClientDocumentTypeDTO;
import com.bootcamp.java.repository.IClientDocumentTypeRepository;
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
public class ClientDocumentTypeServiceImpl implements  IClientDocumentTypeService {
    @Autowired
    private IClientDocumentTypeRepository cRepository;
    @Autowired
    private ClientDocumentTypeConverter cConverter;
    @Override
    public Flux<ClientDocumentTypeDTO> findAll() {
        log.debug("findAll executing");
        Flux<ClientDocumentTypeDTO> dataFClientDocumentTypeDTO = cRepository.findAll()
                .map(clientClientDocumentTypeEntity -> cConverter.ConvertEntityToDTO(clientClientDocumentTypeEntity));
        return dataFClientDocumentTypeDTO;
    }

    @Override
    public Mono<ClientDocumentTypeDTO> findByIdClientDocumentType(Integer IdClientDocumentType) {
        log.debug("finById");
        Mono<ClientDocumentTypeDTO> dataMClientDTO= cRepository.findByIdClientDocumentType(IdClientDocumentType)
                .map(clientEntity -> cConverter.ConvertEntityToDTO(clientEntity));
        return dataMClientDTO;
    }
}
