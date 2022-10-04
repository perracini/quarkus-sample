package org.acme.quarkus.sample;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class TitularConverter {
	
	private final DependenteConverter dependenteConverter;

	public TitularEntity dtoToEntity(Titular dto) {
		if(dto == null) {
			return null;
		}
		TitularEntity entity = new TitularEntity();
		if (dto.getTitularId() != null) {
			entity.setTitularId(dto.getTitularId());
		}
		entity.setName(dto.getName());
		for (Dependente d : dto.getDependentes()) {
			DependenteEntity de = dependenteConverter.dtoToEntity(d);
			entity.addDependente(de);
		}
		return entity;
	}

	public Titular entityToDto(TitularEntity entity) {
		if(entity == null) {
			return null;
		}
		Titular dto = new Titular();
		dto.setTitularId(entity.getTitularId());
		dto.setName(entity.getName());
		if(entity.getDependentes() != null) {
			List<Dependente> dependentes = entity.getDependentes().stream()
					.map(t -> dependenteConverter.entityToDto(t)).collect(Collectors.toList());
			dto.getDependentes().addAll(dependentes);
		}
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
