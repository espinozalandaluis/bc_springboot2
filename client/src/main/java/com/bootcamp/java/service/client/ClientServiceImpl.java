package com.bootcamp.java.service.client;

import com.bootcamp.java.common.exception.FunctionalException;
import com.bootcamp.java.converter.ClientConverter;
import com.bootcamp.java.dto.ClientResponseDTO;
import com.bootcamp.java.dto.ClientDTO;
import com.bootcamp.java.repository.IClientDocumentTypeRepository;
import com.bootcamp.java.repository.IClientRepository;
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
public class ClientServiceImpl implements IClientService{

    @Autowired
    private IClientRepository cRepository;

    @Autowired
    private IClientTypeRepository ctRepository;

    @Autowired
    private IClientDocumentTypeRepository cdtRepository;
    @Autowired
    private ClientConverter cConverter;
    @Override
    public Flux<ClientResponseDTO> findAll() {
        log.debug("findAll executing");
        return cRepository.findAll()
                .flatMap(client ->ctRepository.findByIdClientType(client.getIdClientType())
                        .flatMap(clientType -> cdtRepository.findByIdClientDocumentType(client.getIdClientDocumentType())
                                .map(clientDocumentType -> cConverter.EntityToDTOResponse(client,clientType,clientDocumentType))));
    }

    @Override
    public Mono<ClientResponseDTO> findByDocumentNumber(String documentNumber) {
        log.debug("finById");
        return cRepository.findByDocumentNumber(documentNumber)
                .flatMap(client ->ctRepository.findByIdClientType(client.getIdClientType())
                        .flatMap(clientType -> cdtRepository.findByIdClientDocumentType(client.getIdClientDocumentType())
                                .map(clientDocumentType -> cConverter.EntityToDTOResponse(client,clientType,clientDocumentType))))
                .switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El documento %s no existe",documentNumber))));
    }

    @Override
    public Mono<ClientDTO> findByIdClient(Integer idClient) {
        log.debug("finById");
        Mono<ClientDTO> dataMClientDTO= cRepository.findByIdClient(idClient)
                .map(clientEntity -> cConverter.ConvertEntityToDTO(clientEntity));
        return dataMClientDTO;
    }

    @Override
    public Mono<ClientDTO> create(ClientDTO clientDTO) {
        log.debug("create executing {}",clientDTO);
        return ctRepository.findByIdClientType(clientDTO.getIdClientType()).flatMap(existingClientType ->{
            log.debug("find object client type object executing {}", existingClientType);
            return cdtRepository.findByIdClientDocumentType(clientDTO.getIdClientDocumentType()).flatMap(existingClientDocumentType -> {
                log.debug("find object client document type object executing {}", existingClientType);
                return cRepository.save(cConverter.ConvertDTOToEntity(clientDTO))
                        .map(client -> cConverter.ConvertEntityToDTO(client));
            }).switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El idClientDocumentType %s no existe",clientDTO.getIdClientDocumentType()))));
        }).switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El idClientType %s no existe",clientDTO.getIdClientType()))));
    }

    @Override
    public Mono<ClientDTO> update(Integer idClient,ClientDTO clientDTO) {
        log.debug("update executing {} {}", idClient,clientDTO);
        log.debug("update executed",clientDTO);
        return cRepository.findByIdClient(idClient)
                .flatMap(dbClient ->{
                    return ctRepository.findByIdClientType(clientDTO.getIdClientType()).flatMap(existingClientType ->{
                        log.debug("find object client type object executing {}", existingClientType);
                        return cdtRepository.findByIdClientDocumentType(clientDTO.getIdClientDocumentType()).flatMap(existingClientDocumentType -> {
                            return cRepository.save(cConverter.ConvertDTOToEntity(clientDTO))
                                    .map(client -> cConverter.ConvertEntityToDTO(client));
                        }).switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El idClientDocumentType %s no existe",clientDTO.getIdClientDocumentType()))));
                    }).switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("El idClientType %s no existe",clientDTO.getIdClientType()))));
        });
    }

    @Override
    public Mono<ClientDTO> delete(Integer idClient) {
        log.debug("delete executing {}",idClient);
        return cRepository.findByIdClient(idClient).flatMap(existingClient ->{
            log.debug("delete object executing {}", existingClient);
            return cRepository.delete(existingClient).then(Mono.just(cConverter.ConvertEntityToDTO(existingClient)));
        }).switchIfEmpty(Mono.error(() -> new FunctionalException(String.format("No se puede eliminar, el codigo de cliente %s no existe",idClient))));
    }
}
