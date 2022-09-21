package org.acme.quarkus.sample;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.acme.quarkus.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class InstrutorService {
	
	private final InstrutorConverter instrutorConverter;
	private final InstrutorRepository instrutorRepository;
	private final TurmaService turmaService;
	
    public List<Instrutor> findAll() {
    	return instrutorRepository.findAll()
    			.list()
    			.stream()
    			.map(element -> instrutorConverter.entityToDto(element))
    			.collect(Collectors.toList());
    	
    }

    public Optional<Instrutor> findById(@NonNull Integer instrutorId) {
    	Optional<InstrutorEntity> entity =  instrutorRepository.findByIdOptional(instrutorId); 	
    	return entity.map(element -> instrutorConverter.entityToDto(element));
    }

    @Transactional
    public InstrutorEntity save(@Valid Instrutor instrutor) {
    	 log.debug("Saving Instrutor: {}", instrutor);
         InstrutorEntity entity = instrutorConverter.dtoToEntity(instrutor);
         instrutorRepository.persist(entity);
         return entity;
    }

    @Transactional
    public void update(@Valid Instrutor instrutor) {
        log.debug("Updating Instrutor: {}", instrutor);
        if (Objects.isNull(instrutor.getInstrutorId())) {
            throw new ServiceException("Instrutor does not have an instrutorId");
        }
        InstrutorEntity entity = instrutorRepository.findByIdOptional(instrutor.getInstrutorId())
                .orElseThrow(() -> new ServiceException("No Instrutor found for instrutorId[%s]", instrutor.getInstrutorId()));
        entity = instrutorConverter.dtoToEntity(instrutor, entity);
        instrutorRepository.persist(entity);
        
    }
    
    @Transactional
    public void delete(Integer instrutorId) {
    	Optional<InstrutorEntity> entity =  instrutorRepository.findByIdOptional(instrutorId);
        if(!entity.isPresent()) {
            throw new NotFoundException();
        }
    	turmaService.updateWhenInstrutorDeleted(entity.get());
    	instrutorRepository.delete(entity.get());
    }


}
