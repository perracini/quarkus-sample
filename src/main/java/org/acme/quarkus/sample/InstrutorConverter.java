package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class InstrutorConverter {

	public InstrutorEntity dtoToEntity(Instrutor dto) {
		if(dto == null) {
			return null;
		}
		InstrutorEntity entity = new InstrutorEntity();
		if (dto.getInstrutorId() != null) {
			entity.setInstrutorId(dto.getInstrutorId());
		}
		entity.setName(dto.getName());

		return entity;
	}

	public Instrutor entityToDto(InstrutorEntity entity) {
		if(entity == null) {
			return null;
		}
		Instrutor dto = new Instrutor();
		dto.setInstrutorId(entity.getInstrutorId());
		dto.setName(entity.getName());

		return dto;
	}
	
	public InstrutorEntity dtoToEntity(Instrutor dto, InstrutorEntity entity) {
		if(dto == null) {
			return null;
		}
		if (dto.getInstrutorId() != null) {
			entity.setInstrutorId(dto.getInstrutorId());
		}
		entity.setName(dto.getName());
		return entity;
	}

}
