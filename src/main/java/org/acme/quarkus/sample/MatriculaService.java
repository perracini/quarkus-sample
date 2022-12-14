package org.acme.quarkus.sample;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class MatriculaService {
	
	private final MatriculaRepository matriculaRepository;
	private final MatriculaConverter matriculaConverter;

    @Transactional
    public MatriculaEntity save(@Valid Matricula matricula) {
        log.debug("Saving Turma: {}", matricula);
        MatriculaEntity entity = matriculaConverter.dtoToEntity(matricula);
        matriculaRepository.persist(entity);
        return entity;
    }
    
    
    public Optional<Matricula> findById(@NonNull Integer turmaId, @NonNull Integer alunoId) {
    	MatriculaIdentity i = new MatriculaIdentity();
    	i.setTurmaId(turmaId);
    	i.setAlunoId(alunoId);
    	Optional<MatriculaEntity> entity =  matriculaRepository.findByIdOptional(i); 	
    	return entity.map(element -> matriculaConverter.entityToDto(element));
    }
    
    public List<Object[]> getCustomData(){
    	//usa a propriedade - se fosse native usaria a coluna
    	Query q = matriculaRepository.getEntityManager().createQuery("SELECT DISTINCT t.status, m.status, a.name FROM Turma t "
    			+ "JOIN Matricula m ON t.turmaId = m.matriculaIdentity.turmaId "
    			+ "JOIN Aluno a ON a.alunoId =  m.matriculaIdentity.alunoId");
    	@SuppressWarnings("unchecked")
		List<Object[]> customData = q.getResultList();
    	
    	return customData;
    	
    }
}
