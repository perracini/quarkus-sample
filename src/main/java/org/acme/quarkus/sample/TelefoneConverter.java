package org.acme.quarkus.sample;

import javax.enterprise.context.ApplicationScoped;

import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class TelefoneConverter {
	
	public TelefoneEntity dtoToEntity(Telefone dto) {
		if(dto == null) {
			return null;
		}
		TelefoneEntity entity = new TelefoneEntity();
		if (dto.getTelefoneId() != null) {
			entity.setTelefoneId(dto.getTelefoneId());
		}
		entity.setDdd(dto.getDdd());
		entity.setNumber(dto.getNumber());

		return entity;
	}
	
	public Telefone entityToDto(TelefoneEntity entity) {
		if(entity == null) {
			return null;
		}
		Telefone dto = new Telefone();
		dto.setTelefoneId(entity.getTelefoneId());
		dto.setDdd(entity.getDdd());
		dto.setNumber(entity.getNumber());
		
		return dto;
	}
	
	public TelefoneEntity dtoToEntity(Telefone dto, TelefoneEntity entity) {
		if(dto == null) {
			return null;
		}
		if (dto.getTelefoneId() != null) {
			entity.setTelefoneId(dto.getTelefoneId());
		}
		entity.setDdd(dto.getDdd());
		entity.setNumber(dto.getNumber());
		return entity;
	}

}
