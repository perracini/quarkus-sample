package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class AlunoConverter {

	public AlunoEntity dtoToEntity(Aluno dto) {
		if(dto == null) {
			return null;
		}
		AlunoEntity entity = new AlunoEntity();
		if (dto.getAlunoId() != null) {
			entity.setAlunoId(dto.getAlunoId());
		}
		entity.setName(dto.getName());

		return entity;
	}

	public Aluno entityToDto(AlunoEntity entity) {
		if(entity == null) {
			return null;
		}
		Aluno dto = new Aluno();
		dto.setAlunoId(entity.getAlunoId());
		dto.setName(entity.getName());
		
		return dto;
	}
	
	public AlunoEntity dtoToEntity(Aluno dto, AlunoEntity entity) {
		if(dto == null) {
			return null;
		}
		if (dto.getAlunoId() != null) {
			entity.setAlunoId(dto.getAlunoId());
		}
		entity.setName(dto.getName());
		return entity;
	}

}
