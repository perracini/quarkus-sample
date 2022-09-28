package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.acme.quarkus.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class TelefoneService {

	private final TelefoneConverter telefoneConverter;
	private final TelefoneRepository telefoneRepository;
	private final InstrutorRepository instrutorRepository;

	public List<Telefone> findAll() {
		return telefoneRepository.findAll().list().stream().map(element -> telefoneConverter.entityToDto(element))
				.collect(Collectors.toList());

	}

	public List<Telefone> findAllInstrutor(Integer instrutorId) {

		InstrutorEntity i = instrutorRepository.findByIdOptional(instrutorId).orElseThrow(
				() -> new ServiceException("No Instrutor found for instrutorId[%s]", instrutorId));

		return telefoneRepository.findByInstrutor(i).stream().map(element -> telefoneConverter.entityToDto(element))
				.collect(Collectors.toList());

	}

	@Transactional
	public List<TelefoneEntity> save(@Valid Instrutor i, InstrutorEntity e) {
		List<TelefoneEntity> lista = new ArrayList<>();
		for (Telefone telefone : i.getTelefones()) {
			log.debug("Saving Telefone: {}", telefone);
			TelefoneEntity entity = telefoneConverter.dtoToEntity(telefone);
			entity.setInstrutor(e);
			telefoneRepository.persist(entity);
			lista.add(entity);
		}
		return lista;
	}
	
	@Transactional
	public void update(@Valid Instrutor i) {
		
		InstrutorEntity entity = instrutorRepository.findByIdOptional(i.getInstrutorId()).orElseThrow(
				() -> new ServiceException("No Instrutor found for instrutorId[%s]", i.getInstrutorId()));
		
		
		List<TelefoneEntity> toCompare = telefoneRepository.findByInstrutor(entity);
		List<TelefoneEntity> toRemove = new ArrayList<>();
		
		for(TelefoneEntity ent : toCompare) {
			boolean removing = true;
			for (Telefone telefone : i.getTelefones()) {
				if(ent.getTelefoneId().equals(telefone.getTelefoneId())) {
					removing = false;
				}
			}
			if(removing) {
				toRemove.add(ent);
			}
		}
		
		
		i.getTelefones().forEach(telefone -> {
			log.debug("Updating Telefone: {}", telefone);
			TelefoneEntity telEntity = new TelefoneEntity();
			if(telefone.getTelefoneId() != null) {
				telEntity = telefoneRepository.findById(telefone.getTelefoneId());
			}
			
			telEntity.setDdd(telefone.getDdd());
			telEntity.setNumber(telefone.getNumber());
			telEntity.setInstrutor(entity);
			telefoneRepository.persist(telEntity);
					
		});
		
		
		toRemove.forEach(rem -> {
			telefoneRepository.delete(rem);
		});
	
	}
	
	@Transactional
	public void delete(Integer instrutorId) {
		
		InstrutorEntity entity = instrutorRepository.findByIdOptional(instrutorId).orElseThrow(
				() -> new ServiceException("No Instrutor found for instrutorId[%s]", instrutorId));
		
		List<TelefoneEntity> toRemove = telefoneRepository.findByInstrutor(entity);	
		
		for (TelefoneEntity rem : toRemove) {
			telefoneRepository.delete(rem);
		}
	}
	
	

}
