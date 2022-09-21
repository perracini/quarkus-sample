package org.acme.quarkus.sample;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class MatriculaConverter {
	
	private final AlunoRepository alunoRepository;
	private final TurmaRepository turmaRepository;
	
	public MatriculaEntity dtoToEntity(Matricula domain) {
		if (domain == null) {
			return null;
		}
		
		MatriculaEntity entity = new MatriculaEntity();
		MatriculaIdentity i = new MatriculaIdentity();
		
		if (domain.getAlunoId() != null && domain.getTurmaId() != null) {
			Optional<AlunoEntity> a = alunoRepository.findByIdOptional(domain.getAlunoId());
			Optional<TurmaEntity> t = turmaRepository.findByIdOptional(domain.getTurmaId());
			
			if (a.isPresent() && t.isPresent()) {
				i.setAlunoId(a.get().getAlunoId());
				i.setTurmaId(t.get().getTurmaId());
				entity.setMatriculaIdentity(i);
			}
		}
		
		if(domain.getStatus() != null) {
			entity.setStatus(domain.getStatus());
		}
		
		return entity;
	}
	
	public Matricula entityToDto(MatriculaEntity entity) {	
		if ( entity == null ) {
            return null;
        }

        Matricula domain = new Matricula();
     
        domain.setAlunoId(entity.getMatriculaIdentity().getAlunoId());
        domain.setTurmaId(entity.getMatriculaIdentity().getTurmaId());
        domain.setStatus(entity.getStatus());
        
        return domain;

	}

}
