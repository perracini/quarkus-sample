package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class DependenteConverter {

	public DependenteEntity dtoToEntity(Dependente dto) {
		if(dto == null) {
			return null;
		}
		DependenteEntity entity = new DependenteEntity();
		if (dto.getDependenteId() != null) {
			entity.setDependenteId(dto.getDependenteId());
		}
		entity.setName(dto.getName());

		return entity;
	}

	public Dependente entityToDto(DependenteEntity entity) {
		if(entity == null) {
			return null;
		}
		Dependente dto = new Dependente();
		dto.setDependenteId(entity.getDependenteId());
		dto.setName(entity.getName());
		if(entity.getTitular() != null) {
			dto.setTitularId(entity.getTitular().getTitularId());
		}
		
		
		return dto;
	}
	
	public DependenteEntity dtoToEntity(Dependente dto, DependenteEntity entity) {
		if(dto == null) {
			return null;
		}
		if (dto.getDependenteId() != null) {
			entity.setDependenteId(dto.getDependenteId());
		}
		entity.setName(dto.getName());
		return entity;
	}

}
