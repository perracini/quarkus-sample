package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class TitularConverter {

	public TitularEntity dtoToEntity(Titular dto) {
		if(dto == null) {
			return null;
		}
		TitularEntity entity = new TitularEntity();
		if (dto.getTitularId() != null) {
			entity.setTitularId(dto.getTitularId());
		}
		entity.setName(dto.getName());

		return entity;
	}

	public Titular entityToDto(TitularEntity entity) {
		if(entity == null) {
			return null;
		}
		Titular dto = new Titular();
		dto.setTitularId(entity.getTitularId());
		dto.setName(entity.getName());
		
		return dto;
	}
	
	public TitularEntity dtoToEntity(Titular dto, TitularEntity entity) {
		if(dto == null) {
			return null;
		}
		if (dto.getTitularId() != null) {
			entity.setTitularId(dto.getTitularId());
		}
		entity.setName(dto.getName());
		return entity;
	}

}
