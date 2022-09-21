package org.acme.quarkus.sample;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.acme.quarkus.exception.ServiceException;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class AlunoService {
	
	private final AlunoConverter alunoConverter;
	private final AlunoRepository alunoRepository;
	
    public List<Aluno> findAll() {
    	return alunoRepository.findAll()
    			.list()
    			.stream()
    			.map(element -> alunoConverter.entityToDto(element))
    			.collect(Collectors.toList());
    	
    }
    
    public List<Aluno> findPage(int pageNumber, int pageSize) {
    	
    	PanacheQuery<AlunoEntity> alunos = alunoRepository.findAll();
    	List<AlunoEntity> lista = alunos.page(Page.of(pageNumber, pageSize)).list();
    	
    	return lista
    			.stream()
    			.map(element -> alunoConverter.entityToDto(element))
    			.collect(Collectors.toList());
    		
    	
    }

    public Optional<Aluno> findById(@NonNull Integer alunoId) {
    	Optional<AlunoEntity> entity =  alunoRepository.findByIdOptional(alunoId); 	
    	return entity.map(element -> alunoConverter.entityToDto(element));
    }

    @Transactional
    public AlunoEntity save(@Valid Aluno aluno) {
    	 log.debug("Saving Aluno: {}", aluno);
         AlunoEntity entity = alunoConverter.dtoToEntity(aluno);
         alunoRepository.persist(entity);
         return entity;
    }

    @Transactional
    public void update(@Valid Aluno aluno) {
        log.debug("Updating Aluno: {}", aluno);
        if (Objects.isNull(aluno.getAlunoId())) {
            throw new ServiceException("Aluno does not have an alunoId");
        }
        AlunoEntity entity = alunoRepository.findByIdOptional(aluno.getAlunoId())
                .orElseThrow(() -> new ServiceException("No Aluno found for alunoId[%s]", aluno.getAlunoId()));
        entity = alunoConverter.dtoToEntity(aluno, entity);
        alunoRepository.persist(entity);
        
    }

}
