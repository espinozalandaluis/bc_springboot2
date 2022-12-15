package com.bootcamp.java.service.clientType;

import com.bootcamp.java.converter.ClientTypeConverter;
import com.bootcamp.java.dto.ClientTypeDTO;
import com.bootcamp.java.repository.IClientTypeRepository;
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
public class ClientTypeServiceImpl implements IClientTypeService {
    @Autowired
    private IClientTypeRepository cRepository;
    @Autowired
    private ClientTypeConverter cConverter;
    @Override
    public Flux<ClientTypeDTO> findAll() {
        log.debug("findAll executing");
        Flux<ClientTypeDTO> dataFClientTypeDTO = cRepository.findAll()
                .map(clientTypeEntity -> cConverter.ConvertEntityToDTO(clientTypeEntity));
        return dataFClientTypeDTO;
    }

    @Override
    public Mono<ClientTypeDTO> findByIdClientType(Integer IdClientType) {
        log.debug("finById");
        Mono<ClientTypeDTO> dataMClientDTO= cRepository.findByIdClientType(IdClientType)
                .map(clientEntity -> cConverter.ConvertEntityToDTO(clientEntity));
        return dataMClientDTO;
    }
}
