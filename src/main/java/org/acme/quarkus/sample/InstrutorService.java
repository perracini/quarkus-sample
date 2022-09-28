package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import org.acme.quarkus.exception.ServiceException;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class InstrutorService {

	private final InstrutorConverter instrutorConverter;
	private final TelefoneConverter telefoneConverter;
	private final InstrutorRepository instrutorRepository;
	private final TurmaService turmaService;
	private final TelefoneService telefoneService;
	private final TelefoneRepository telefoneRepository;

	public List<Instrutor> findAll() {
		
		List<InstrutorEntity> lista = instrutorRepository.findAll().list();
		
		List<Instrutor> instrutores = new ArrayList<>();
		
		for(InstrutorEntity i : lista) {
			List<Telefone> lista2 = telefoneRepository.findByInstrutor(i).stream().map(t -> telefoneConverter.entityToDto(t)).collect(Collectors.toList());
			Instrutor inst = instrutorConverter.entityToDto(i);
			inst.getTelefones().addAll(lista2);
			instrutores.add(inst);
		}
		
		return instrutores;

	}

	public Optional<Instrutor> findById(@NonNull Integer instrutorId) {
		
		InstrutorEntity entity = instrutorRepository.findByIdOptional(instrutorId).orElseThrow(
				() -> new ServiceException("No Instrutor found for instrutorId[%s]", instrutorId));
		
		Optional<InstrutorEntity> entityOpt = instrutorRepository.findByIdOptional(instrutorId);
		Optional<Instrutor> i = entityOpt.map(element -> instrutorConverter.entityToDto(element));
		if(i.isPresent()) {
			List<Telefone> lista = telefoneRepository.findByInstrutor(entity).stream().map(t -> telefoneConverter.entityToDto(t)).collect(Collectors.toList());
			i.get().getTelefones().addAll(lista);
		}
		return i;
	}

	@Transactional
	public InstrutorEntity save(@Valid Instrutor instrutor) {
		log.debug("Saving Instrutor: {}", instrutor);
		InstrutorEntity entity = instrutorConverter.dtoToEntity(instrutor);

		instrutorRepository.persist(entity);
		return entity;
	}

	@Transactional
	public void update(@Valid Instrutor instrutor) {
		log.debug("Updating Instrutor: {}", instrutor);
		if (Objects.isNull(instrutor.getInstrutorId())) {
			throw new ServiceException("Instrutor does not have an instrutorId");
		}
		InstrutorEntity entity = instrutorRepository.findByIdOptional(instrutor.getInstrutorId()).orElseThrow(
				() -> new ServiceException("No Instrutor found for instrutorId[%s]", instrutor.getInstrutorId()));
		
		entity = instrutorConverter.dtoToEntity(instrutor, entity);
		
		instrutorRepository.persist(entity);

	}

	@Transactional
	public void delete(Integer instrutorId) {
		Optional<InstrutorEntity> entity = instrutorRepository.findByIdOptional(instrutorId);
		if (!entity.isPresent()) {
			throw new NotFoundException();
		}
		telefoneService.delete(instrutorId);
		turmaService.updateWhenInstrutorDeleted(entity.get());
		instrutorRepository.delete(entity.get());
	}

}
