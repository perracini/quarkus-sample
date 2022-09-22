package org.acme.quarkus.sample;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class TurmaConverter {
	
	private final InstrutorRepository instrutorRepository;
	private final AlunoRepository alunoRepository;
	private final InstrutorConverter instrutorConverter;
	private final AlunoConverter alunoConverter;

	public Turma entityToDto(TurmaEntity entity) {
		   if ( entity == null ) {
	            return null;
	        }

	        Turma turma = new Turma();

	        turma.setTurmaId( entity.getTurmaId() );
	        if ( entity.getStartDate() != null ) {
	            turma.setStartDate(entity.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	        }
	        if ( entity.getEndDate() != null ) {
	            turma.setEndDate(entity.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	        }

	        if(entity.getInstrutor() != null) {
	        	turma.setInstrutorId(entity.getInstrutor().getInstrutorId());
	        }
	        
	        if(entity.getMonitor() != null) {
	        	turma.setAlunoId(entity.getMonitor().getAlunoId());
	        }
	        if(entity.getStatus() != null) {
	        	turma.setStatus(entity.getStatus());
	        }
	        return turma;
	}
	
	public TurmaEntity dtoToEntity(Turma domain) {
		if (domain == null) {
			return null;
		}

		TurmaEntity turmaEntity = new TurmaEntity();

		if (domain.getInstrutorId() != null) {
			Optional<InstrutorEntity> i = instrutorRepository.findByIdOptional(domain.getInstrutorId());
			if (i.isPresent()) {
				turmaEntity.setInstrutor(i.get());
			}
		}

		if (domain.getAlunoId() != null) {
			Optional<AlunoEntity> a = alunoRepository.findByIdOptional(domain.getAlunoId());
			if (a.isPresent()) {
				turmaEntity.setMonitor(a.get());
			}
		}

		if (domain.getStartDate() != null) {
			turmaEntity.setStartDate(LocalDate.parse(changeFormatDate(domain.getStartDate())));
		}
		if (domain.getEndDate() != null) {
			turmaEntity.setEndDate(LocalDate.parse(changeFormatDate(domain.getEndDate())));
		}
		if (domain.getTurmaId() != null) {
			turmaEntity.setTurmaId(domain.getTurmaId());
		}
		if (domain.getStatus() != null) {
			turmaEntity.setStatus(domain.getStatus());
		}

		return turmaEntity;
	}
	
	public TurmaFullDto entityToFullDto(TurmaEntity entity) {
		   if ( entity == null ) {
	            return null;
	        }

		   TurmaFullDto turma = new TurmaFullDto();

	        turma.setTurmaId( entity.getTurmaId() );
	        if ( entity.getStartDate() != null ) {
	        	turma.setStartDate(entity.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	        }
	        if ( entity.getEndDate() != null ) {
	        	turma.setEndDate(entity.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
	        }

	        if(entity.getInstrutor() != null) {
	        	turma.setInstrutor(instrutorConverter.entityToDto(entity.getInstrutor()));
	        }
	        
	        if(entity.getMonitor() != null) {
	        	turma.setAluno(alunoConverter.entityToDto(entity.getMonitor()));
	        }
	        if(entity.getStatus() != null) {
	        	turma.setStatus(entity.getStatus());
	        }
	        return turma;
	}
	
	public TurmaEntity dtoToEntity(Turma domain, TurmaEntity entity) {
		if (domain == null) {
			return null;
		}

		if (domain.getInstrutorId() != null) {
			Optional<InstrutorEntity> i = instrutorRepository.findByIdOptional(domain.getInstrutorId());
			if (i.isPresent()) {
				entity.setInstrutor(i.get());
			}
		}

		if (domain.getAlunoId() != null) {
			Optional<AlunoEntity> a = alunoRepository.findByIdOptional(domain.getAlunoId());
			if (a.isPresent()) {
				entity.setMonitor(a.get());
			}
		}

		if (domain.getStartDate() != null) {
			entity.setStartDate(LocalDate.parse(changeFormatDate(domain.getStartDate())));
		}
		if (domain.getEndDate() != null) {
			entity.setEndDate(LocalDate.parse(changeFormatDate(domain.getEndDate())));
		}
		if (domain.getTurmaId() != null) {
			entity.setTurmaId(domain.getTurmaId());
		}
		if (domain.getStatus() != null) {
			entity.setStatus(domain.getStatus());
		}
		return entity;
	}
	
	public String changeFormatDate(String date) {
	    DateTimeFormatter formatterFrom = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // 1
	    LocalDate localDateFrom = LocalDate.parse(date, formatterFrom); // 2

	    DateTimeFormatter formatterTo = DateTimeFormatter.ISO_LOCAL_DATE; // 3
	    return localDateFrom.format(formatterTo); //4
	}

}
