package org.acme.quarkus.sample;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.acme.quarkus.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class TurmaService {
	
    private final TurmaRepository turmaRepository;
    private final TurmaConverter turmaConverter;
    private final InstrutorRepository instrutorRepository;
    
    public List<Turma> findAll() {
    	return turmaRepository.findAll()
    			.list()
    			.stream()
    			.map(element -> turmaConverter.entityToDto(element))
    			.collect(Collectors.toList());
    }
    
    @Transactional
    public TurmaEntity save(@Valid Turma turma) {
        log.debug("Saving Turma: {}", turma);
        TurmaEntity entity = turmaConverter.dtoToEntity(turma);
        turmaRepository.persist(entity);
        return entity;
    }
    
    public Optional<TurmaFullDto> findById(@NonNull Integer turmaId) {
    	Optional<TurmaEntity> entity =  turmaRepository.findByIdOptional(turmaId); 	
    	return entity.map(element -> turmaConverter.entityToFullDto(element));
    }
    
    @Transactional
    public void update(@Valid Turma turma) {
        log.debug("Updating Turma: {}", turma);
        if (Objects.isNull(turma.getTurmaId())) {
            throw new ServiceException("Turma does not have an turmaId");
        }
        TurmaEntity entity = turmaRepository.findByIdOptional(turma.getTurmaId())
                .orElseThrow(() -> new ServiceException("No Turma found for turmaId[%s]", turma.getTurmaId()));
        entity = turmaConverter.dtoToEntity(turma, entity);
        turmaRepository.persist(entity);
        
    }
    
    @Transactional
    public void updateWhenInstrutorDeleted(InstrutorEntity i) {
    		turmaRepository.update("instrutor = NULL where instrutor = ?1", i);
    		 
    }
    
    @Transactional
    public List<Turma> getTurmasWithInstrutor(Integer instrutorId) {
    	InstrutorEntity entity = instrutorRepository.findByIdOptional(instrutorId)
                .orElseThrow(() -> new ServiceException("No Instrutor found for instrutorId[%s]", instrutorId));
    	return turmaRepository.find("instrutor", entity)
 			.list()
			.stream()
			.map(element -> turmaConverter.entityToDto(element))
			.collect(Collectors.toList());
    		 
    }
    
    
    @Transactional
    public List<Turma> getTurmasEncerradas() {
    		
    		return turmaRepository.find("#teste", LocalDate.now())
    		.stream()
			.map(element -> turmaConverter.entityToDto(element))
			.collect(Collectors.toList());
    		 
    }
}

